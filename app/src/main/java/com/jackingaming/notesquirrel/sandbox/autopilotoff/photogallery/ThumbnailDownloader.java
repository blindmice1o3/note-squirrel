package com.jackingaming.notesquirrel.sandbox.autopilotoff.photogallery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Ch. 27 Loopers, Handlers, and HandlerThread (Android Programming: The Big Nerd Ranch Guide)
// and (https://blog.mindorks.com/android-core-looper-handler-and-handlerthread-bd54d69fe91a)
//
// The [generic argument] called [Token] is used to identify each download.
// You can use any object as a token. "In this case, the [ImageView] makes for a
// convenient token, since that is where the downloaded images will eventually go."
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    Handler handler;            // Associated with [this] thread's Looper.
    Map<Token, String> requestMap =
            Collections.synchronizedMap(new HashMap<Token, String>());
    Handler responseHandler;    // Associated with [main] thread's Looper.

    // Listener interface to communicate the responses with.
    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }
    public void setListener(Listener<Token> listener) {
        this.listener = listener;
    }
    Listener<Token> listener;

    // The [Handler] passed in should be associated with the main thread's [Looper].
    // The passed in [Handler] maintains its loyalty to the [Looper] of the thread
    // that created it. Any messages the [Handler] is responsible for will be
    // handled on the main thread's queue.
    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        this.responseHandler = responseHandler; // Used to access the main thread.
    }

    // "Here, Android Lint will warn you about subclassing [Handler]. Your [Handler] will be
    // kept alive by its [Looper]. So if your [Handler] is an anonymous inner class, it is
    // easy to leak memory accidentally through an implicit object reference. Here, though,
    // everything is tied to your [HandlerThread], so there is no danger of leaking anything."
    @SuppressLint("HandlerLeak")
    // This method is called before the [Looper] checks the queue for the first time.
    // This makes it a good place to create your [Handler] implementation
    // (e.g. [Handler.handleMessage(...)]).
    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Check the message type
                if (msg.what == MESSAGE_DOWNLOAD) {
                    // "This is necessary because [Token] is a generic class argument,
                    // but Message.obj is an [Object]. Due to type erasure, it is not
                    // possible to actually make this cast."
                    @SuppressWarnings("unchecked")
                    // Retrieve the [Token]
                    Token token = (Token) msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    // This is where the downloading happens.
                    handleRequest(token);
                }
            }
        };
    }

    /**
     * Add [Token]-[URL] pair to the repository of URLs (a synchronized [HashMap]).
     * Obtain a message, give it the [Token] as its [obj].
     * Send it off to be put on the thread's message queue.
     *
     * @param token a key used to store and retrieve the URL associated with a particular Token
     * @param url the URL for where each [GalleryItem]'s thumbnail-size photo lives
     */
    public void queueThumbnail(Token token, String url) {
        Log.i(TAG, "Got an URL: " + url);
        // Store the [URL] associated with a particular [Token] in a synchronized [HashMap].
        requestMap.put(token, url);

        // [Message]s are tasks that need to be handled. They contain several fields,
        // three are relevant to this implementation: [what], [obj], and [target].
        // [what] - a user-defined int that describes the message.
        // [obj] - a user-specified object to be sent with the message.
        // [target] - the [Handler] that will handle the message.
        //
        // [Handler] is short for "message handler." When you create a [Message], it will
        // automatically be attached to a [Handler]. And when your [Message] is ready to be
        // processed, [Handler] will be the object in charge of making it happen.
        // A [Handler] is not just a target for processing your [Message]s.
        // A [Handler] is your interface for creating and posting [Message]s too.
        //
        // A [Handler] is attached to exactly one [Looper}.
        // A [Message] is attached to exactly one target [Handler].
        // A [Looper] has a whole queue of [Message]s.
        // Multiple [Handler]s can be attached to one [Looper]. This means that your [Handler]'s
        // [Message]s may be living side by side with another [Handler]'s messages.
        handler
                .obtainMessage(MESSAGE_DOWNLOAD, token)
                .sendToTarget();    // Send [Message] to its [Handler], [Handler] will put
                                    // [Message] on the end of [Looper]'s message queue.
        // When the looper gets to a particular message in the queue, it gives the message
        // to the message's target to handle. Typically, the message is handled in the
        // target's implementation of [Handler.handlerMessage(...)] (see [onLooperPrepared()]).
    }

    /**
     * Use [FlickrFetchr] to download bytes from the URL and then turn these bytes into a bitmap.
     * @param token a key used to store and retrieve the URL associated with a particular Token
     *              (e.g. the [ImageView] where the downloaded image will eventually go)
     */
    private void handleRequest(final Token token) {
        try {
            // Retrieve the [URL] associated with a particular [Token] from a synchronized [HashMap].
            final String url = requestMap.get(token);
            // Check for the existence of a [URL].
            if (url == null) {
                return;
            }

            // Download bytes from the [URL].
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            // Use [BitmapFactory] to construct a bitmap with the array of bytes.
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");

            // At this point, you must set the bitmap on the [ImageView] that originally
            // came from [GalleryItemAdapter]. However, this is UI work, so it must be
            // done on the main thread.
            //
            // [Handler.post(Runnable)] is a convenience method for posting [Message]s. It
            // sets the [Message]'s [callback] field with the passed in [Runnable]. When a
            // [Message] has its [callback] field set, instead of being run by its
            // [Handler] [target], the [Runnable] in [callback] is run instead.
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Double-check the requestMap. This is necessary because the [GridView]
                    // recycles its views. By the time [ThumbnailDownloader] finishes downloading
                    // the [Bitmap], [GridView] may have recycled the [ImageView] and requested
                    // a different URL for it. This check ensures that each [Token] gets the
                    // correct image, even if another request has been made in the meantime.
                    if (requestMap.get(token) != url) {
                        return;
                    }

                    // Remove the [Token] from the requestMap
                    requestMap.remove(token);
                    // Set the bitmap on the [Token] (in this case, an [ImageView]).
                    listener.onThumbnailDownloaded(token, bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

    // If the user rotates the screen, [ThumbnailDownloader] may be hanging on to
    // invalid [ImageView]s. Bad things will happen if those [ImageView]s get pressed
    // (called in [PhotoGalleryFragment.onDestroyView()]).
    public void clearQueue() {
        // Clean all the requests out of your queue.
        handler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
}
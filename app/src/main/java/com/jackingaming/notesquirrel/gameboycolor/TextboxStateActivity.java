package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class TextboxStateActivity extends AppCompatActivity {

    private TextView textBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textbox_state);

        textBox = (TextView) findViewById(R.id.textview_textbox_state);
        Log.d(MainActivity.DEBUG_TAG, "TextboxStateActivity.onCreate(Bundle) setting TextView's text...");
        textBox.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Enim praesent elementum facilisis leo vel fringilla est. Ultrices gravida dictum fusce ut placerat orci nulla. In mollis nunc sed id semper risus in. Egestas dui id ornare arcu. Nisi lacus sed viverra tellus in hac habitasse. Quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum. Consequat mauris nunc congue nisi vitae suscipit tellus mauris a. Risus in hendrerit gravida rutrum quisque non tellus orci ac. Diam sollicitudin tempor id eu nisl nunc mi ipsum. Pellentesque eu tincidunt tortor aliquam nulla facilisi cras fermentum odio.\n" +
                "\n" +
                "Quisque egestas diam in arcu cursus euismod quis. Nibh mauris cursus mattis molestie a iaculis at erat. Commodo quis imperdiet massa tincidunt nunc pulvinar sapien et. Nunc mi ipsum faucibus vitae aliquet nec. Pellentesque elit eget gravida cum. A scelerisque purus semper eget duis at tellus at urna. Posuere urna nec tincidunt praesent semper feugiat nibh. Amet nulla facilisi morbi tempus. Vestibulum lorem sed risus ultricies. Mi quis hendrerit dolor magna eget est lorem ipsum. Tristique nulla aliquet enim tortor at. A condimentum vitae sapien pellentesque. Sit amet justo donec enim diam. Leo vel orci porta non pulvinar neque laoreet suspendisse. Blandit massa enim nec dui nunc mattis enim ut. Imperdiet proin fermentum leo vel orci. Tellus rutrum tellus pellentesque eu tincidunt tortor aliquam nulla facilisi.\n" +
                "\n" +
                "Molestie a iaculis at erat pellentesque adipiscing commodo. Ipsum consequat nisl vel pretium lectus quam id. Id porta nibh venenatis cras sed felis eget velit. Leo vel orci porta non pulvinar. Pharetra et ultrices neque ornare aenean euismod elementum nisi quis. Vehicula ipsum a arcu cursus vitae congue. Enim ut tellus elementum sagittis vitae et leo. Arcu vitae elementum curabitur vitae. Ut etiam sit amet nisl purus. Rhoncus dolor purus non enim praesent elementum facilisis leo vel. Sed enim ut sem viverra aliquet eget. Amet mattis vulputate enim nulla aliquet porttitor lacus luctus accumsan. Mi bibendum neque egestas congue quisque. Porta lorem mollis aliquam ut porttitor leo a diam sollicitudin. Consequat nisl vel pretium lectus quam id leo in. Dui faucibus in ornare quam viverra orci sagittis eu volutpat.\n" +
                "\n" +
                "Congue mauris rhoncus aenean vel elit scelerisque. Interdum consectetur libero id faucibus nisl tincidunt eget nullam. Adipiscing elit pellentesque habitant morbi tristique. Dignissim suspendisse in est ante in nibh mauris cursus mattis. Velit aliquet sagittis id consectetur purus ut. Ultricies tristique nulla aliquet enim tortor at auctor urna. Ac feugiat sed lectus vestibulum. At in tellus integer feugiat scelerisque varius. Id neque aliquam vestibulum morbi blandit cursus risus at. Magna sit amet purus gravida quis blandit. Gravida quis blandit turpis cursus in hac habitasse platea dictumst. Ac turpis egestas maecenas pharetra convallis. Tellus at urna condimentum mattis.\n" +
                "\n" +
                "Et pharetra pharetra massa massa ultricies mi. Pulvinar neque laoreet suspendisse interdum consectetur libero. Sit amet venenatis urna cursus eget nunc scelerisque. Adipiscing enim eu turpis egestas pretium aenean pharetra magna ac. Eu volutpat odio facilisis mauris sit amet massa. Congue mauris rhoncus aenean vel. Facilisis sed odio morbi quis commodo odio aenean sed adipiscing. Tristique senectus et netus et malesuada fames ac turpis egestas. Risus quis varius quam quisque id. Egestas congue quisque egestas diam in. Est sit amet facilisis magna etiam tempor orci eu lobortis. Pretium aenean pharetra magna ac. Vel facilisis volutpat est velit. Aliquam sem fringilla ut morbi tincidunt augue interdum velit. Adipiscing elit pellentesque habitant morbi. Scelerisque varius morbi enim nunc faucibus.\n" +
                "\n" +
                "Diam donec adipiscing tristique risus nec feugiat. Interdum velit laoreet id donec ultrices tincidunt. Magna sit amet purus gravida quis blandit turpis cursus in. Arcu bibendum at varius vel pharetra vel turpis nunc. Sapien nec sagittis aliquam malesuada bibendum. Sem fringilla ut morbi tincidunt augue interdum velit euismod in. Iaculis urna id volutpat lacus laoreet non curabitur gravida arcu. Volutpat sed cras ornare arcu dui vivamus arcu felis. Pharetra diam sit amet nisl. Tristique et egestas quis ipsum. Facilisis mauris sit amet massa vitae tortor. Dis parturient montes nascetur ridiculus mus mauris. Placerat orci nulla pellentesque dignissim enim. Quis viverra nibh cras pulvinar mattis nunc. Nam aliquam sem et tortor consequat.\n" +
                "\n" +
                "Justo laoreet sit amet cursus sit amet. Tortor posuere ac ut consequat semper viverra nam libero justo. Est lorem ipsum dolor sit amet consectetur adipiscing elit. Habitasse platea dictumst vestibulum rhoncus est pellentesque. Mi tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. Et odio pellentesque diam volutpat commodo sed egestas. Risus in hendrerit gravida rutrum quisque non tellus orci ac. Egestas sed tempus urna et pharetra pharetra. Consequat nisl vel pretium lectus quam id leo. Pretium fusce id velit ut.\n" +
                "\n" +
                "Elementum facilisis leo vel fringilla est ullamcorper. Congue nisi vitae suscipit tellus. Consectetur a erat nam at lectus urna duis convallis convallis. Nisi vitae suscipit tellus mauris a diam. Sodales ut etiam sit amet nisl. Lectus urna duis convallis convallis. Amet nisl purus in mollis. Dignissim enim sit amet venenatis urna cursus eget nunc. Parturient montes nascetur ridiculus mus. Sed risus ultricies tristique nulla aliquet enim tortor. Ut eu sem integer vitae justo eget magna fermentum. Sollicitudin ac orci phasellus egestas tellus rutrum tellus. Massa eget egestas purus viverra accumsan in.\n" +
                "\n" +
                "Enim sit amet venenatis urna cursus eget nunc scelerisque viverra. In nibh mauris cursus mattis molestie a iaculis at erat. Congue mauris rhoncus aenean vel. Curabitur gravida arcu ac tortor. Amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus. Diam sollicitudin tempor id eu nisl nunc mi ipsum faucibus. Amet facilisis magna etiam tempor orci eu lobortis elementum nibh. Ultrices tincidunt arcu non sodales neque sodales ut etiam sit. Ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue eget. Auctor elit sed vulputate mi sit amet mauris commodo quis. Vitae et leo duis ut diam quam nulla porttitor. Morbi tristique senectus et netus et malesuada fames ac. Adipiscing elit pellentesque habitant morbi tristique senectus et. Sed pulvinar proin gravida hendrerit.\n" +
                "\n" +
                "Aenean pharetra magna ac placerat vestibulum lectus mauris. Purus in mollis nunc sed id semper. Bibendum enim facilisis gravida neque convallis a cras. Quis vel eros donec ac odio tempor orci dapibus. Mauris augue neque gravida in fermentum et sollicitudin ac. Et egestas quis ipsum suspendisse ultrices gravida dictum fusce. Rhoncus mattis rhoncus urna neque. Vulputate eu scelerisque felis imperdiet proin. Mattis molestie a iaculis at erat pellentesque. Consectetur adipiscing elit duis tristique sollicitudin.");
    }

}
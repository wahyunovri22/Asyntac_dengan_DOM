package creative.can.com.sabtu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textview);
        textView.setText("Daftar Buku\n");
        try {
            InputStream is = getAssets().open("praktikum.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nodelist = doc.getElementsByTagName("buku");
            for (int i =0; i<nodelist.getLength(); i++){
                Node buku = nodelist.item(i);

                String ISBN = buku.getChildNodes().item(1).getTextContent();
                String judul = buku.getChildNodes().item(3).getTextContent();
                String pengarang = buku.getChildNodes().item(5).getTextContent();

                textView.setText(textView.getText() + "\nISBN :" + ISBN);
                textView.setText(textView.getText() + "\nJudul Buku :" + judul);
                textView.setText(textView.getText() + "\nPengarang :" + pengarang);

            }


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

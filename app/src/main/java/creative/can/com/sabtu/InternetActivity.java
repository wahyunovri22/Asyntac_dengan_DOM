package creative.can.com.sabtu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cz.msebera.android.httpclient.Header;

public class InternetActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        textView = (TextView)findViewById(R.id.textview);
        textView.setText("Daftar Buku\n");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("https://seputarsemarang.000webhostapp.com/api/praktikum.xml", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    InputStream is = new ByteArrayInputStream(responseBody);
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
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(InternetActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

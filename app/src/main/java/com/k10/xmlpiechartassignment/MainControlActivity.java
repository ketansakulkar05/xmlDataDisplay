package com.k10.xmlpiechartassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainControlActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> passTest = new ArrayList<>();
    ArrayList<String> errorTest = new ArrayList<>();
    ArrayList<String> skipTest = new ArrayList<>();
    ArrayList<String> failTest = new ArrayList<>();
    ArrayList<String> extraTest = new ArrayList<>();
    ArrayList<String> Component = new ArrayList<>();


    Map<String, ArrayList<String>> PieChartData = new HashMap<>();
    Map<String, Long> linechartData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);


        findViewById(R.id.displayPie_btn).setOnClickListener(this);
        findViewById(R.id.lineGraph_btn).setOnClickListener(this);
        documentBuilderMethod();
    }

    private void documentBuilderMethod() {

        try {

            InputStream is = getAssets().open("runtests.output.all.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(is);
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());

            NodeList aCaseList = document.getElementsByTagName("Case");
            NodeList componentList = document.getElementsByTagName("Component");

            for (int temp = 0; temp < aCaseList.getLength(); temp++) {
                Node node = aCaseList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;

                    String elementname = eElement.getAttribute("Status");
                    if (elementname.equalsIgnoreCase("PASS")) {
                        passTest.add(elementname);
                    } else if (elementname.equalsIgnoreCase("ERROR")) {
                        errorTest.add(elementname);

                    } else if (elementname.equalsIgnoreCase("SKIP")) {
                        skipTest.add(elementname);
                    } else if (elementname.equalsIgnoreCase("FAIL")) {
                        failTest.add(elementname);
                    } else {
                        extraTest.add(elementname);
                    }

                    System.out.println(" result Status : " + eElement.getAttribute("Status"));

                }
            }


            System.out.println("Component" + componentList.getLength());    //Just a separator
            for (int temp = 0; temp < componentList.getLength(); temp++) {
                Node compNode = componentList.item(temp);
                System.out.println("");    //Just a separator
                if (compNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) compNode;
//                    Component.add();
                    String value = eElement.getAttribute("TotalElapsedTime");
                    value = value.replaceAll("\n", "").replaceAll("&#xA;|&#xD;", "");
                    linechartData.put(eElement.getAttribute("Name"), Long.valueOf(value.trim()));

                    String elementname = eElement.getAttribute("TotalElapsedTime");

                    System.out.println(" result TotalElapsedTime : " + elementname);

                }
            }



        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            PieChartData.put("PASS", passTest);
            PieChartData.put("FAIL", failTest);
            PieChartData.put("ERROR", errorTest);
            PieChartData.put("SKIP", skipTest);
            PieChartData.put("OTHER", extraTest);

        }
    }

    @Override
    public void onClick(View v) {
        Intent feature = null;

        switch (v.getId()) {
            case R.id.displayPie_btn:
                feature = new Intent(this, DisplayPieChartActivity.class);
                feature.putExtra("pieData", (Serializable) PieChartData);
                break;
            case R.id.lineGraph_btn:
                feature = new Intent(this, LineGraphActivity.class);
                feature.putExtra("LineData", (Serializable) linechartData);
                break;
        }
        startActivity(feature);
    }
}

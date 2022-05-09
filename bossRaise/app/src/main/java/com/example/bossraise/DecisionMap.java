package com.example.bossraise;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static java.lang.Integer.*;

public class DecisionMap extends AppCompatActivity {

    DecisionNode head;
    DecisionNode tail;


    // constructor loads up data file
    public DecisionMap(Context context) throws IOException, CSVFormatError, invalidCSVitem, Resources.NotFoundException {
        InputStream inFile = context.getResources().openRawResource(R.raw.dataraise);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inFile, Charset.forName("UTF-8")));
        buildUnorderedList(reader);
        buildOrderedMap();
    }
    

    // add node to unordered list
    private void append(DecisionNode newNode)  {
        if(isEmpty())  {
            this.head = newNode;
            this.tail = newNode;
            this.tail.setLinkedNode(null);

            return;
        }

        // previous tail links to new tail
        this.tail.setLinkedNode(newNode);
        this.tail = newNode;
    }



    // converts each line from csv file into node and adds it to list
    public void buildUnorderedList(BufferedReader reader) throws IOException, CSVFormatError, invalidCSVitem {

        DecisionNode node;
        String line = "";
        int numLine = 0;

        // throw error if CSV file is empty
        if(!reader.ready())  {
            throw new CSVFormatError("CSV file appears to be empty");
        }

        while( (line = reader.readLine()) != null)  {
            // split data by comma and append to array
            line.split(",");

            // read data
            numLine ++;
            node = buildNode(line);
            append(node);
        }
    }

    // grab IDs and link paths for yes and no decisions
    public void buildOrderedMap()  {

        DecisionNode nodeLinker = head;
        int nodeID = nodeLinker.getNodeID();

        while(nodeLinker != null)  {
            nodeID = nodeLinker.getNodeID();

            int option1id = nodeLinker.getOption1id();
            int option2id = nodeLinker.getOption2id();
            int option3id = nodeLinker.getOption3id();

            DecisionNode option1 = nodeFetch(option1id);
            DecisionNode option2 = nodeFetch(option2id);
            DecisionNode option3 = nodeFetch(option3id);

            nodeLinker.setOption1(option1);
            nodeLinker.setOption2(option2);
            nodeLinker.setOption3(option3);

            nodeLinker = nodeLinker.getLinkedNode();
        }
        cleanup();
    }

    private int getID(String[] arr, int index)  {
        for (char c : arr[index].toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new invalidCSVitem("All node IDs must be integer, error found at node " + arr[0]);
            }
        }
        return parseInt(arr[index]);
    }

    // converts CSV line into a readable node
    private DecisionNode buildNode(String line)   {
        String[] stringArray = line.split(",");
        DecisionNode n = new DecisionNode();

        // Check CSV file has correct amount of items per line
        if(stringArray.length != 9)  {
            throw new CSVFormatError("wrong amount of items on CSV line for node " + stringArray[0]);
        }

        n.setNodeID(getID(stringArray, 0));
        n.setOption1id(getID(stringArray, 1));
        n.setOption2id(getID(stringArray, 2));
        n.setOption3id(getID(stringArray, 3));

        n.setDescription(stringArray[4]);
        n.setQuestion(stringArray[5]);

        n.setOp1Description(stringArray[6]);
        n.setOp2Description(stringArray[7]);
        n.setOp3Description(stringArray[8]);

        return n;
    }

    public DecisionNode entryPoint()  { return head;  }

    // navigates through map to grab any node based on ID
    private DecisionNode nodeFetch(int nodeID)  {

        DecisionNode nodeLinker = head;

        while(nodeLinker != null)  {
            if(nodeLinker.getNodeID() == nodeID){break;}
            nodeLinker = nodeLinker.getLinkedNode();
        }

        return nodeLinker;
    }

    private void cleanup() {
        if (head == null) {
            return;
        }

        DecisionNode currentNode = head;
        DecisionNode nextNode = head.getLinkedNode();

        while (nextNode != null) {

            currentNode.setLinkedNode(null);

            currentNode = nextNode;
            nextNode = currentNode.getLinkedNode();
        }
    }

    private boolean isEmpty()  { return this.head == null; }
}

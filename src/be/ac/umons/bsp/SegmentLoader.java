package be.ac.umons.bsp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by clement on 2/20/16.
 */

public class SegmentLoader {

    private String path;

    public SegmentLoader(String path) {
        this.path = path;
    }

    /**
     * Reads the file and adds all the segments in it to a list
     * @return a list containing all the segments in the file
     */
    public List<Segment> loadAsList() {

        LinkedList<Segment> segmentList = new LinkedList<Segment>();

        try {
            FileInputStream inputFile = new FileInputStream(path);
            DataInputStream in = new DataInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            line = br.readLine(); //First line contains information about the file

            while((line = br.readLine()) != null) {
                String[] temp = line.split(" ");
                segmentList.add(new Segment(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]), Double.parseDouble(temp[3]), temp[4]));
            }

            in.close();
        }
        catch (Exception e) {
            System.err.println("Error : "+e.getMessage()+"\n"+"Cause : "+e.getCause());
        }

        return segmentList;
    }

    public static void main(String [] args) {
        //TODO remove || This is a test to make sure files are loaded properly in a list
        /*
        SegmentLoader test = new SegmentLoader("assets/first/octangle.txt");
        List<Segment> test2 = test.loadAsList();

        for (Segment temp : test2) {
            System.out.println(temp.getX1()+" "+temp.getY1()+" "+temp.getX2()+" "+temp.getY2()+" "+temp.getC());
        }
        */
    }
}

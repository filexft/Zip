/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package V1;

import java.io.File;
import java.io.IOException;
import sae_graph.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author lycee
 */
public class Graph {
    
    
    private ArrayList<Node> listNode;
    private HashMap<Node, ArrayList<Edge>> adjacList;
    
    public Graph(){
        listNode = new ArrayList<Node>();
        adjacList = new HashMap<Node, ArrayList<Edge>>();
    }
    
    public boolean addNode(String id, String type){
        Node node = new Node(id, type);
        listNode.add(node);
        adjacList.put(node,new ArrayList<Edge>());
        return true;
    }
    public boolean addEdge(Node orig, Node dest, double fiab, int dist, int temp){
        if(!listNode.contains(orig)){
            ArrayList<Edge> tempList = new ArrayList<Edge>();
            tempList.add(new Edge(dest, fiab, dist, temp));
            adjacList.put(orig, tempList);
            listNode.add(orig);
            return true;
        }
        /**
         * trying remove the destination part becauese it add 2 time the adjact nodes
         */
        /*if(!listNode.contains(dest)){
            ArrayList<Edge> tempList = new ArrayList<Edge>();
            tempList.add(new Edge(orig, fiab, dist, temp));
            adjacList.put(dest, tempList);
            listNode.add(dest);
            return true;
        }*/
        adjacList.get(orig).add(new Edge(dest, fiab, dist, temp));
        //adjacList.get(dest).add(new Edge(orig, fiab, dist, temp));
        return true;
    }
    
    public ArrayList<Node> getNodeList(String node){
        return listNode;
    }
    public ArrayList<Node> getAdjacNodeStringList(Node node){
        ArrayList<Node> returnList = new ArrayList<Node>();
        for (Edge e : adjacList.get(node)){
            returnList.add(e.getDest());
        }
        return returnList;
    }
    
    public ArrayList<Edge> getAdjacNodeObjectList(Node node){
        return adjacList.get(node);
    }
    
    public void fillGraph(){
        File listAdjac = new File("liste-adjacence-jeuEssai.csv");
        File listSucc = new File("liste-successeurs.csv");
        try{
            
            Scanner scAdj = new Scanner(listAdjac);
            Scanner scSucc = new Scanner(listSucc);
            
            String[] adjacSeparatedLine;
            String[] succSeparatedLine;
            
            String succLine;
            String adjLine;
            
            /*
            ArrayList<String> succLineList = new ArrayList<String>();
            while (scSucc.hasNext()) {
                System.out.println(scSucc.nextLine());
                succLineList.add(scSucc.nextLine());
            }
            */
            ArrayList<String> adjLineList = new ArrayList<String>();
            while (scAdj.hasNext()) {
              adjLineList.add(scAdj.nextLine());
            }
            
            
            boolean start = false;
            
            //add all nodes
            for(String adline: adjLineList){
                adjLine = adline;
                if(adjLine.startsWith("S1"))
                {
                    start = true;
                }
                if(start){
                    adjacSeparatedLine = adjLine.split(";");
                    addNode(adjacSeparatedLine[0], adjacSeparatedLine[1]);
                }
            }
            
            start = false;
            for(String adline: adjLineList){
                
                
                adjLine = adline;
                if(adjLine.startsWith("S1"))
                {
                    start = true;
                }
                if(start){
                        //2nd line take a line from a succ file and copy from index 1 till end
                        succLine = scSucc.nextLine();
                        succSeparatedLine = Arrays.copyOfRange(succLine.split(";"), 1, succLine.split(";").length);


                        /**
                         * change the String array of int into into array of int and sort them
                         */
                        int[] myIntArray = new int[succSeparatedLine.length];

                        for (int i = 0; i < succSeparatedLine.length; i++) {
                            myIntArray[i] = Integer.parseInt(succSeparatedLine[i]);
                        }
                        sortStringArray(myIntArray);


                        adjacSeparatedLine = Arrays.copyOfRange(adjLine.split(";"), 2, adjLine.split(";").length);
                        int col = 0;
                        for (String cell: adjacSeparatedLine){
                            if(!cell.equals("0")){
                                Node sourceNode = findNodeByString(adjLine.split(";")[0]);
                                Node destNode = findNodeByNum(myIntArray[col]);
                                String[] cellData = cell.split(",");
                               
                                /**
                                 * --Use Trim to remove the problem of not reading the int 
                                 * url : https://stackoverflow.com/questions/43535578/why-is-the-integer-parseint-not-working-in-my-java-code
                                 */
                                //System.out.println("Source :" + sourceNode.getId() + " destination :" + destNode.getId() +  "  fiab  " + cellData[0] + " dist:" + cellData[1] + " temp: " + cellData[2]);
                                //System.out.println("\n");
                                
                                double fiab = Double.parseDouble(cellData[0]);
                                int dist = Integer.parseInt(cellData[1].trim());
                                int temp = Integer.parseInt(cellData[2].trim());
                                
                                addEdge(sourceNode, destNode, 10, 10,10);
                                col++;
                            }
                        }
                    
                  
                    }
                    
                    
                    
                    //--get the Node (name , type)
                    //adjacSeparatedLine = adjLine.split(";");
                    //System.out.println(adjacSeparatedLine[0] + " :  " + adjacSeparatedLine[1]);
                    
                    //System.out.println("\n\n");
                    
                }
                //System.out.println(s);            
            
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public Node findNodeByNum(int num){
        for(Node n: listNode){
            if(n.getId().equals("S" + num)){
                return n;
            }
        }
        return null;
    }
    public Node findNodeByString(String str){
        for(Node n: listNode){
            if(n.getId().equals(str)){
                return n;
            }
        }
        return null;
    }
    private void sortStringArray(int[] str){
        int size = str.length;
        for (int i = 0; i < size - 1; i++){
            for (int j = i+1; j < size ; j++){
                if(str[i] > str[j]){
                    int tmp = str[i];
                    str[i] = str[j];
                    str[j] = tmp;
                }
            }
        }
    }

    public void printGraph(){
        
        for(Map.Entry<Node, ArrayList<Edge>> set: adjacList.entrySet()){
            String out = ""+ set.getKey().getId() +"(" + set.getKey().getType() + ")";
            for(Edge e: set.getValue()){
                out += " -> " + e.getDest().getId();
            }
            System.out.println(out);
        }
    }
    
    public void printNodeList(){
        for(Node n: listNode){
            System.out.println(n.getId());
        }
    }
    @Override
    public String toString() {
        return "Graph{" + "listNode=" + listNode.toString() + ", adjacList=" + adjacList.toString() + '}';
    }
   
    
    
}

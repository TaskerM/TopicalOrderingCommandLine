
/**
 * Class: CMSC350
 * Author: Mark Tasker
 * Date: 12/15/19
 * Project Description: This project will behave like a Java command line compiler.
 *      It's meant to recompile a class and those classes that rely on it. The code
 *      of this particular class is meant to establish the graph information for
 *      the dependencies. Contains vertices, edges, and exception handling.
 * 
 */

 
import java.io.*;
import java.util.*;
 
class DependencyGraph<T> {
 
    private Map<T, Integer> classes;
    private ArrayList<LinkedList<Integer>> adjacentNodes;
    private int numOfNodes = 0;
    private List<Integer> vistedNodes;
    private StringBuilder builder;
 
    public DependencyGraph() {
        adjacentNodes = new ArrayList<>();
        classes = new HashMap<>();
    }
 
    public void build(ArrayList<T[]> lines) {
        for (T[] line : lines) {
            for (int i = 0; i < line.length; i++) {
                addVertex(line[i]);
                if (i != 0) {
                    addEdge(line[0], line[i]);
                }
            }
        }
    }
 
    ArrayList<String[]> parseFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        ArrayList<String[]> lines = new ArrayList<>();
        String line;
        int index = 0;
 
        while ((line = br.readLine()) != null) {
            String[] items = line.split("\\s");
            lines.add(index, items);
            index++;
        }
 
        return lines;
    }
 
    private void addVertex(T className) {
        if (classes.containsKey(className)) {
            return;
        }
 
        classes.put(className, numOfNodes);
        LinkedList<Integer> adj = new LinkedList<>();
        adjacentNodes.add(numOfNodes, adj);
        numOfNodes++;
 
    }
 
    private void addEdge(T source, T destination) {
        int from = classes.get(source);
        int to = classes.get(destination);
        adjacentNodes.get(from).add(to);
    }
 
    public String topoOrder(T start) throws Exception {
        if (classes.get(start) == null) {
            throw new Exception();
        }
 
        builder = new StringBuilder();
        vistedNodes = new ArrayList<>();
        dfs(classes.get(start));
        return builder.toString();
    }
 
    private String getClassName(int vertex) {
        for (T k : classes.keySet()) {
            if (classes.get(k).equals(vertex)) {
                return k.toString();
            }
        }
        return "";
    }
 
    private void dfs(int v) throws Exception {
        builder.append(getClassName(v)).append(" ");
        for (Integer x : adjacentNodes.get(v)) {
            if (vistedNodes.contains(x)) {
                throw new Exception();
            }
            vistedNodes.add(x);
            dfs(x);
        }
    }
}

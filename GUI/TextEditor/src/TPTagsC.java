//File: TouchPadHTML.java
//Author: Kevin Gleason
//Date: Monday, April 7, 2014
//Use: TouchPadHTML Tags Deque implementation

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

public class TPTagsC<T> implements TPTags<T>{
    //Instance Variables
    private Deque<T> tags;
    private int indent;
    private Deque<T> previous;

    public TPTagsC(int indent){
        this.tags = new ArrayDeque<T>();
        this.indent = 0;
        this.previous = new ArrayDeque<T>();
    }

    //Rip off next tag in line.
    public String rip(){
        if(!this.tags.isEmpty()) {

            //Take off of previous list too
            this.previous.pop();
            String tagIn = (String) this.tags.pop();
            String tagOut = tagIn;
            if (!this.checkNewLine(tagIn)) tagOut += "\n";
            if (this.checkIndent(tagIn)) this.indent -= 1;
            if (this.noIndent(tagIn)) return tagOut;
            return this.getBuffer() + tagOut;
        }
        else throw new NoSuchElementException("Tags List Empty");
    }

    //Peek at next tag
    private T peek(){
        return this.previous.peek();
    }

    // Returns true for elements that require indentation counter to increment
    private boolean checkIndent(String tag){
        if (tag.equals("</div>")  ||
            tag.equals("</form>") ||
            tag.equals("</ul>")   ||
            tag.equals("</ol>"))
                return true;
        return false;
    }

    // Returns true for elements that are not preceeded by an indent.
    private boolean noIndent(String tag) {
        if (
                tag.equals("</p>")     ||
                tag.equals("</li>")    ||
                tag.equals("</h1>")    ||
                tag.equals("</a>")     ||
                tag.equals("</center>")||
                tag.equals("</label>") ||
                tag.equals("</b>")     ||
                tag.equals("</i>"))
            return true;
        else if (
                tag.equals("<p>")     ||
                tag.equals("<li>")    ||
                tag.equals("<h1>")    ||
                tag.equals("<a>")     ||
                tag.equals("<center>")||
                tag.equals("<label>") ||
                tag.equals("<b>")     ||
                tag.equals("<i>"))
            return true;
        System.out.println("NoIndent - NOPE!" + tag);
        return false;
    }

    // Returns true for elements that are not followed by a new line
    private boolean checkNewLine(String tag){
        if (tag.equals("</span>")  ||  //NEED SPAN
            tag.equals("</h1>")||
            //tag.equals("</center>")||
            tag.equals("</i>")     ||
            tag.equals("</b>"))
                return true;
        return false;
    }

    public void push(T s){
        String text = (String) s;
        if (checkIndent(text)) this.indent += 1;
        this.tags.push(s);
    }

    public boolean isEmpty(){
        return this.tags.isEmpty();
    }

    public boolean indent(){
        if (this.previous.isEmpty()) return false;
        return this.noIndent((String) this.previous.peek());
    }

    public int size() {
        return this.tags.size();
    }

    public int getIndent() {
        return this.indent;
    }

    public String getBuffer() {
        String buffer = "";
        for (int i = 0; i < this.indent; i++){
            buffer += "\t";
        }
        return buffer;
    }

    public void setPrevious(T tag){
        this.previous.push(tag);
    }

    public String toString(){
        return this.tags.toString();
    }

    public static void main(String[] args) {
        TPTags t = new TPTagsC<String>(0);
        t.push("Hello");
        t.push("Goodbye");
        System.out.println(t.rip());
        System.out.println(t.rip());
    }
}

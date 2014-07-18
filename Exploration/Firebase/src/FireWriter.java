/**
 * Created by GleasonK on 7/18/14.
 */
import com.firebase.client.*;
import com.firebase.client.snapshot.Node;

public class FireWriter {
    private Firebase firebase = new Firebase("https://flickering-fire-9754.firebaseio.com");

    public void writeFire(String location, String message){
        this.firebase.child(location).setValue(message);
//        this.firebase.setValue(location,message);message
//        this.firebase.
    }

    public void setMetrics(String project, int CPUvalue, int memory, double time){
        String trial = this.firebase.child(project).getName();
        System.out.println(trial);
        this.firebase.child(project+"/CPU").setValue(CPUvalue);
        this.firebase.child(project+"/Memory").setValue(memory);
        this.firebase.child(project+"/Time").setValue(time);
    }

    public void getRoot(){
        Firebase root = this.firebase.getRoot();
//        root.setValue("Hi");
    }

    public void getData() {
        //Set the data by a time stamp.
//        DataSnapshot ds = new DataSnapshot(this.firebase,);
    }

    public static void main(String[] args) {
        FireWriter fw = new FireWriter();
        fw.setMetrics("App01", 100, 59, 0.00005430);
    }
}

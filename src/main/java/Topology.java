import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class Topology {
    public static void main(String [] args){
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("words", new Spout());
        builder.setBolt("exclaim1", new ExclamationBolt())
                .shuffleGrouping("words");
        builder.setBolt("exclaim2", new ExclamationBolt())
                .shuffleGrouping("exclaim1");

        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(2);


        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("testTopology", conf, builder.createTopology());
        Utils.sleep(30000);
        cluster.killTopology("testTopology");
        cluster.shutdown();
    }
}

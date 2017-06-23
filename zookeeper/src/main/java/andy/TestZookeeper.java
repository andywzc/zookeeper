package andy;

import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;


/**
 * Created by Andy on 2017/6/23.
 */
public class TestZookeeper {
    private static final Logger logger = Logger.getLogger(TestZookeeper.class);
    public static void main(String[] args) throws Exception {
        String path = "/myNode";
        ZooKeeper zooKeeper = ZKUtil.getConn();
        logger.debug(zooKeeper);
        try{
            ZKUtil.createNode(path, "andy");

            String node = ZKUtil.getNode(path);
            logger.debug(node);

            ZKUtil.setNode(path, "anwen", 0);
            node = ZKUtil.getNode(path);
            logger.debug("修改后  ： " + node);

            ZKUtil.delNode(path, 1);

        } catch (Exception e) {
            logger.error(e, e.getCause());
        } finally {
            ZKUtil.closeConn();
        }



    }
}

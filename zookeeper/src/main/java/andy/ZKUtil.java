package andy;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by Andy on 2017/6/23.
 */
public class ZKUtil {
    private static final Logger logger = Logger.getLogger(ZKUtil.class);
    private static final String CONNSTR = "192.168.52.128:2181";
    private static final int SESSION_TIMEOUT  = 30 * 1000;
    private static ZooKeeper zooKeeper;



    public static ZooKeeper getConn() throws IOException {
        zooKeeper = new ZooKeeper(CONNSTR, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                logger.debug("hello");
            }
        });
        return zooKeeper;
    }

    public static void closeConn() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

    public static void createNode(String path, String data) throws KeeperException, InterruptedException {
        if (!exists(path)) {
            zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public static String getNode(String path) throws KeeperException, InterruptedException {
        String value = null;
        if (exists(path)) {
            byte[] data = zooKeeper.getData(path, false, new Stat());
            value = new String(data);
        }

        return value;
    }

    public static String setNode(String path, String data, int version) throws KeeperException, InterruptedException {
        String value = null;
        if (exists(path)) {
            Stat stat = zooKeeper.setData(path, data.getBytes(), version);
            value = stat.toString();
        }
        return value;
    }

    public static void delNode(String path, int version) throws KeeperException, InterruptedException {
        if (exists(path)) {
            zooKeeper.delete(path, version);
        }
    }

    public static boolean exists(String path) throws KeeperException, InterruptedException {
        boolean exists = false;
        if (StringUtils.isNotEmpty(path)) {
            exists = zooKeeper.exists(path, null) != null;
        }
        return exists;
    }



}

package com.zookeeperwebclient.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class NodeController {

    @Autowired
    ZkClient zkClient;

    private static final Logger logger = LoggerFactory.getLogger(NodeController.class);

    @GetMapping(name="/**", produces = "application/json")
    @ResponseBody
    public NodeJsonModel listNode(HttpServletRequest request) throws Exception{

        URL url = new URL(request.getRequestURL()+request.getRequestURI());
        String uri = request.getRequestURI();

        logger.info("Get request: "+uri);

        CuratorFramework client = zkClient.getclient();

        Map<String, Map<String, String>> nodes = new HashMap<String, Map<String, String>>();
        for (String i: client.getChildren().forPath(uri)){

            Map<String, String> node = new HashMap  <String, String>();

            node.put("link", new URL(url, i).toString());
            nodes.put(i, node);
        }

        Map<String, Map<String, String>> aclNodes = new HashMap<String, Map<String, String>>();
        for (ACL acl: client.getACL().forPath(uri)){

            Map<String, String> node = new HashMap<String, String>();
            node.put("acl", acl.toString());
            aclNodes.put(acl.getId().getId(), node);
        }

        Stat2 stat = new Stat2(client.checkExists().forPath(uri));

        NodeJsonModel nodeModel = new NodeJsonModel();
        nodeModel.setData( new String(client.getData().watched().forPath(uri)));
        nodeModel.setStat(stat);
        nodeModel.setAclNodes(aclNodes);
        nodeModel.setChildrens(nodes);

        return nodeModel;
    }
}
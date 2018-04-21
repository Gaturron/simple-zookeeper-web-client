package com.zookeeperwebclient.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class NodeController {

    @Autowired
    ZkClient zkClient;

    private static final Logger logger = LoggerFactory.getLogger(NodeController.class);

    @GetMapping(name="/**", produces = "application/json")
    @ResponseBody
    public Object listNode(@RequestParam(name="find", required = false) String action,
                                  HttpServletRequest request) throws Exception{

        if(action != null){
            return find(request);
        }
        return getInfo(request);
    }

    private List<NodeJsonModel> find(HttpServletRequest request) throws Exception {

        URL url = new URL(request.getRequestURL()+request.getRequestURI());
        String uri = request.getRequestURI();

        return find(url, uri);
    }

    private List<NodeJsonModel> find(URL url, String uri) throws Exception {

        List<NodeJsonModel> res = new LinkedList<NodeJsonModel>();
        res.add(getInfo(url, uri));

        CuratorFramework client = zkClient.getclient();

        for (String i: client.getChildren().forPath(uri)){

            List<NodeJsonModel>
                    node = find(url, uri+"/"+i);

            if(node.contains("pepe")){
                res.addAll(node);
            }
        }

        return res;
    }

    private NodeJsonModel getInfo(HttpServletRequest request) throws Exception{

        URL url = new URL(request.getRequestURL()+request.getRequestURI());
        String uri = request.getRequestURI();

        logger.info("Get request: "+uri);

        return getInfo(url, uri);
    }

    private NodeJsonModel getInfo(URL url, String uri) throws Exception{

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
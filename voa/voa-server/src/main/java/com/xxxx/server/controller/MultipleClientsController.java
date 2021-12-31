package com.xxxx.server.controller;

import com.xxxx.server.pojo.ComponentMachine;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.ICommentMachineService;
import com.xxxx.server.utils.DateTimeUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;

@RestController
public class MultipleClientsController {

//    @Autowired
//    AuotestUpgradeService auotestUpgradeService;

    @Autowired
    ICommentMachineService componentMachineService;

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 平台获取更新包下载
     */
//    @ResponseBody
//    @ApiOperation(value = "获取信息", notes = "获取信息")
//    @RequestMapping(value = "/client/getUpgradeResources", method = RequestMethod.POST)
//    public Object getUpgradeResources(@RequestBody ComponentMachine componentMachine, HttpServletResponse response)
//    {
//        AutotestUpgrade upgrade = auotestUpgradeService.findBySoftwarename(getResources.getClientName());
//
//        ResponseResources responseResources = new ResponseResources();
//        if(upgrade != null && Integer.parseInt(upgrade.getVersion()) != getResources.getClientId()){
//            responseResources.setCode(300);
//            responseResources.setMsg("需要更新");
//            UpgradeData data = new UpgradeData();
//            data.setDownloadPath(upgrade.getFilepath());
//            responseResources.setData(data);
//            return upgrade;
//        }else {
//            responseResources.setCode(0);
//            responseResources.setMsg("不需要更新");
//            return responseResources;
//        }
//    }

    @RequestMapping("/AutoTest/UpgradeResources")
    @ResponseBody
    public String downLoad(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "";
        if(fileName != null){
            filename = fileName;
        }
        String msg = "";
        String filePath = "D:\\Tangram\\LenovoMultipleClient" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                os.flush();
                os.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----file download---" + filename +"----Date Time--" + DateTimeUtil.getCurrentDateStr());
            msg = "----------file download---" + filename;
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else {
            msg = "文件不存在";
        }
        return msg;
    }

    /**
     * 清除缓存并且重启
     */
    @ResponseBody
    @ApiOperation(value = "获取 Component Status", notes = "获取信息")
    @PostMapping(value = "/api/v1/runMachinesType")
    public RespBean onRunMachinesType(@RequestParam(value = "ips",defaultValue = "") ComponentMachine[] ips,
                                      @RequestParam(value = "runType",defaultValue = "") String runType,
                                      HttpServletRequest request) throws ParseException {
        if (ips == null){
            return RespBean.error("选择的IP为空");
        }

        if(runType.contains("Update Result")){
//            String date = DateTimeUtil.getCurrentDateStr();
//            componentMachineService.UpdateRedis(date);
            return RespBean.success("更新成功");
        }

        for (int i = 0; i < ips.length; i++) {
            String ip = ips[i].getIp();
            int port = 17777;
            //需要服务器的IP地址和端口号，才能获得正确的Socket对象
            Socket socket = new Socket();
            long t1 = 0;
            try {
                t1 = System.currentTimeMillis();
                socket.connect(new InetSocketAddress(ip, port), 3000);
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                //向服务器端发送一条消息
                bw.write(runType);
                bw.flush();
                long t2 = System.currentTimeMillis();
                String msg = "Connect " + ip +" Success, take time -> " + (t2 - t1) + "ms.";
                System.out.println(msg);
            } catch (IOException e) {
                long t2 = System.currentTimeMillis();
                String msg = "Connect " + ip +" failed, take time -> " + (t2 - t1) + "ms.";
                System.out.println(msg);
            }
        }
        return RespBean.success("");
    }


//    @ResponseBody
//    @ApiOperation(value = "更新IP", notes = "更新IP")
//    @PostMapping(value = "/api/v1/ModifyMachineIp")
//    public RespBean changedIP(@RequestBody ChangeMachineIP clientChanged, HttpServletResponse response) {
//
//        if(clientChanged == null)return RespBean.error("空");
//        try{
//            ComponentIpMachine serverIpMachine = componentMachineService.findMachineByIp(clientChanged.getOriginIp());
//            if (serverIpMachine != null){
//                String validBugs = serverIpMachine.getValidBugs();
//                String ip = serverIpMachine.getIp();
//                if(validBugs == null || validBugs != clientChanged.getValidBugs()){
//                    validBugs = clientChanged.getValidBugs();
//                }
//                if(clientChanged.getChangedIp() != null && serverIpMachine.getIp() != clientChanged.getChangedIp()){
//                    ip = clientChanged.getChangedIp();
//                }
//                componentMachineService.UpdateMachineIP(clientChanged.getOriginIp(), ip, validBugs,
//                        DateTimeUtil.getCurrentDateAndTime());
//                JSONObject userJson  = new JSONObject();
//                jsonObject.put("code",20000);
//                jsonObject.put("data", userJson);
//                jsonObject.put("msg","success");
//            }else{
//                jsonObject.put("code", 40000);
//                jsonObject.put("data", null);
//                jsonObject.put("msg", "更新失败");
//            }
//        }catch (Exception ex){
//            jsonObject.put("code", 40000);
//            jsonObject.put("data", null);
//            jsonObject.put("msg", "更新失败");
//        }
//        return jsonObject;
//    }
//
//    /**
//     * 上传测试报告
//     */
//    @ResponseBody
//    @ApiOperation(value = "上传报告", notes = "上传报告")
//    @RequestMapping(value = "/client/pushTestReport", method = RequestMethod.POST)
//    public RespBean pushReport(@RequestBody PushReportRequ pushReport, HttpServletResponse response) throws ParseException {
//        JSONObject object = new JSONObject();
//        try {
//            if(pushReport != null){
//                ComponentIpMachine machine = componentMachineService.findMachineByIp(pushReport.getClientIP());
//                int updateReport = 0;
//                if(machine != null){
//                    updateReport = componentMachineService.UpdateMachineReport(machine.getIp(),pushReport.getReportaddress(),DateTimeUtil.getCurrentDateAndTime());
//                }
//
//                if(updateReport > 0){
//                    object.put("msg", "Send Report Successful");
//                    object.put("code","20000");
//                }else {
//                    object.put("code","400");
//                }
//            }else{
//                object.put("code","400");
//                object.put("msg", "Get Client Ip Failed");
//            }
//
//        }catch (Exception ex){
//            String msg = ex.getMessage();
//            object.put("code","400");
//            object.put("msg", pushReport.getClientIP() + "上传报告");
//        }
//
//        object.put("pushreportinfo", pushReport.toString());
//
//        return object;
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "获取信息", notes = "获取信息")
//    @RequestMapping(value = "/client/getMachineInfo", method = RequestMethod.POST)
//    public Object getMachineInfo(@RequestParam(value = "clientMachineInfos",defaultValue = "") ComponentMachine []clientMachineInfos, HttpServletResponse response) throws ParseException {
//        try {
//            if(clientMachineInfos != null){
//                System.out.println(clientMachineInfos.length);
//                ComponentIpMachine machine = componentMachineService.findMachineByMachineName(clientMachineInfo.getMachineName());
//                if(machine != null){
//                    object.put("code","400");
//                    object.put("data", machine.getIp());
//                    object.put("msg", "Get Client Ip Succeed");
//                }
//            }
//        }catch (Exception ex){
//            String msg = ex.getMessage();
//            object.put("code","400");
//            object.put("msg", msg);
//        }
//        System.out.println(object.toJSONString());
//        return object;
//    }
//
//    @ResponseBody
//    @ApiOperation(value = "设置信息", notes = "设置信息")
//    @RequestMapping(value = "/client/setMachineInfo", method = RequestMethod.POST)
//    public Object setMachineInfo(@RequestBody ClientMachineInfo clientMachineInfo, HttpServletResponse response) throws ParseException {
//        JSONObject object = new JSONObject();
//        try {
//            if(clientMachineInfo != null){
//                System.out.println(clientMachineInfo.toString());
//                ComponentIpMachine machine = componentMachineService.findMachineByMachineName(clientMachineInfo.getMachineName());
//                if(machine == null)
//                {
//                    // 更新 Device name
//                    machine = componentMachineService.findMachineByIp(clientMachineInfo.getMachineIP());
//                    componentMachineService.UpdateDeviceName(clientMachineInfo.getMachineIP(), clientMachineInfo.getMachineName(),clientMachineInfo.getMachineMac());
//                }else{
//                    //更新 IP
//                    componentMachineService.UpdateIpByDeviceName(clientMachineInfo.getMachineIP(), clientMachineInfo.getMachineName(),clientMachineInfo.getMachineMac());
//                }
//            }
//        }catch (Exception ex){
//            String msg = ex.getMessage();
//            object.put("code","400");
//            object.put("msg", msg);
//            ComponentIpMachine machine = componentMachineService.findMachineByIp(clientMachineInfo.getMachineIP());
//            componentMachineService.UpdateDeviceName(machine.getIp(), clientMachineInfo.getMachineName(),clientMachineInfo.getMachineMac());
//        }
//        System.out.println(object.toJSONString());
//        return object;
//    }


}


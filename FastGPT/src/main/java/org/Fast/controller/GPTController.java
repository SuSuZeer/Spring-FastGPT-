package org.Fast.controller;

import cn.hutool.json.JSONUtil;
import org.Fast.dto.Result;
import org.Fast.entity.GPT;
import org.Fast.entity.GPTGroup;
import org.Fast.mapper.GPTGroupMapper;
import org.Fast.mapper.GPTMapper;
import org.Fast.service.GPTService;
import org.Fast.utils.UseFastAPI;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/***
 * @title GPTController
 * @author SUZE
 * @Date 21:08
 **/
@CrossOrigin
@RestController
@RequestMapping("/GPT")
public class GPTController
{
    @Resource
    private GPTService gptService;
    @Resource
    private GPTMapper gptMapper;
    @Resource
    private GPTGroupMapper gptGroupMapper;


    @Resource
    private UseFastAPI useFastAPI;
    /**
     *description<GPT管理>
    []
         * @return org.Fast.dto.Result
         * @author SUZE
         * @time 2024/2/20-14:45
         */
    public GPT ChangeGPT(GPT gpt){
        gpt.setPromptList(JSONUtil.parseArray(gpt.getPrompt()));
        gpt.setPrompt(null);
        return gpt;
    }
    @GetMapping("/GetAppidList")
    public Result getappid(){
        //这里要套一下FastGPT接口 获取appid列表
        return Result.ok(useFastAPI.getAppid());
}

    @PostMapping("/AddGPT")
    public Result Add(@RequestBody GPT gpt){
        gpt.setPrompt(JSONUtil.toJsonStr(gpt.getPromptList()));
        gptMapper.insertGPT(gpt);
        return Result.ok();
    }
    @GetMapping("GetGPTList")
    public Result GetList(){
        List<GPT> allGPTs = gptMapper.getAllGPTs();
        for (GPT gpt :
                allGPTs) {
            gpt=ChangeGPT(gpt);
        }
        return Result.ok(allGPTs);
    }
    @GetMapping("GetOneGPT")
    public Result GetOne(@RequestParam("id") int id){
        GPT gpt = gptMapper.getGPTById((long) id);
        gpt=ChangeGPT(gpt);
        return Result.ok(gpt);
    }
    @PostMapping("UpdataGPT")
    public Result UpData(@RequestBody GPT gpt){
        gpt.setPrompt(JSONUtil.toJsonStr(gpt.getPromptList()));
        gptMapper.updateGPT(gpt);
        return Result.ok(gpt);
}
    @GetMapping("DeleteGPT")
    public Result Delete(@RequestParam("id")int id){
        gptMapper.deleteGPT((long)id);
        return Result.ok();
    }


    /**
     *description<GPT组管理>
    []
         * @return org.Fast.dto.Result
         * @author SUZE
         * @time 2024/2/20-14:44
         */
    public GPTGroup ChangeGPTG(GPTGroup gptGroup){
        gptGroup.setGroupIdList(JSONUtil.parseArray(gptGroup.getGpt_ids()));
        gptGroup.setGpt_ids(null);
        return gptGroup;
    }

    @GetMapping("/GetgptGroupList")
    public Result getGroup(){
        List<GPTGroup> allGPTGroups = gptGroupMapper.getAllGPTGroups();
        System.out.println(allGPTGroups);
        for (GPTGroup gptG :
                allGPTGroups) {
            gptG =ChangeGPTG(gptG);
        }
        return Result.ok(allGPTGroups);
    }
    @GetMapping("/GetGPTGroupOne")
    public Result getGPTGroupById(@RequestParam("id") int id) {
        GPTGroup gptG = gptGroupMapper.getGPTGroupById(id);
        gptG =ChangeGPTG(gptG);
        return Result.ok(gptG);
    }

    @PostMapping("addGPTGroup")
    public Result createGPTGroup(@RequestBody GPTGroup gptGroup) {
        gptGroup.setGpt_ids(JSONUtil.toJsonStr(gptGroup.getGroupIdList()));
        gptGroupMapper.insertGPTGroup(gptGroup);
        return Result.ok();
    }

    @PostMapping("/updateGPTGroup")
    public Result updateGPTGroup(@RequestBody GPTGroup gptGroup) {
        gptGroup.setGpt_ids(JSONUtil.toJsonStr(gptGroup.getGroupIdList()));
        gptGroupMapper.updateGPTGroup(gptGroup);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result deleteGPTGroup(@PathVariable int id) {
        gptGroupMapper.deleteGPTGroup(id);
        return Result.ok();
    }

}

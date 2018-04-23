package cn.ncut.controller.view;

import cn.ncut.controller.base.BaseController;
import cn.ncut.entity.system.ResponseBO;
import cn.ncut.util.HttpUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 多维数据可视化的入口
 * 2018/3/26.
 */
@Controller
@RequestMapping("/view")
public class ViewComtroller extends BaseController {
    /**
     * 根据菜单url获取相应的options
     * 1、获取options ,转为responseBO 出去date
     * 2、data放回
     * 3、前端布局
     * 3.1、动态生成div，设置css属性
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/analysis/{name}")
    public ModelAndView view(@PathVariable("name") String name) {
        ObjectMapper objectMapper = new ObjectMapper();
        ModelAndView mav = this.getModelAndView();
        Integer id = 1;
        if("order".equals(name)){
            id = 1;
        }else{
            id = 2;
        }
        //根据仪表盘name获取仪表盘详情
        String url = "http://www.liangliangempire.cn/view/chart/options/"+id;
        String s = null;
        try {
            s = HttpUtil.get(url);
            s = new String(s.getBytes(), "utf-8");
            ResponseBO responseBO = objectMapper.readValue(s, ResponseBO.class);
            if (responseBO.getData() != null) {
                mav.addObject("data", responseBO.getData());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.addObject("id",id);
        mav.addObject("name", name);
        mav.setViewName("view/dashboard");
        return mav;
    }
    
}

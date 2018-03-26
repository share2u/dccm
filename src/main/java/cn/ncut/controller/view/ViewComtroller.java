package cn.ncut.controller.view;

import cn.ncut.controller.base.BaseController;
import cn.ncut.util.HttpUtil;
import cn.ncut.util.RequestUtil;
import cn.ncut.util.wechat.CommonUtil;
import net.sf.json.JSONObject;
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
public class ViewComtroller  extends BaseController {
    /**
     * 根据菜单url获取相应的options
     * @param name
     * @return
     */
    @RequestMapping(value="/analysis/{name}")
    public ModelAndView view(@PathVariable("name") String name){
        ModelAndView mav = this.getModelAndView();
        //根据仪表盘name获取仪表盘详情
        String url = "http://localhost:8080/view/schema/tables";
        String s =null;
        try {
            s= HttpUtil.get(url);
        }catch (Exception e){
            System.out.println(e);

        }
        mav.addObject("name",name);
        mav.addObject("s",s);
        mav.setViewName("view/dashboard");
        return mav;
    }
}

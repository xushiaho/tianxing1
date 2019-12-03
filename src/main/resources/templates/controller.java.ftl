package ${package.Controller};


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.DateUtils;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import java.util.Arrays;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if businessName??>${businessName}<#else>${table.entityPath}</#if>/")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${entity?uncap_first}Service;

    /**
    * 查询${table.comment!?substring(0,2)}列表
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(){
        Page< ${entity}> page = new Page< ${entity}>();
        return new ApiResult(${entity?uncap_first}Service.page(page, Wrappers.emptyWrapper()));
    }

    /**
    * 新增${table.comment!?substring(0,2)}信息
    *
    * @param ${entity?uncap_first}
    * @return
    */
    @RequestMapping("add")
    @ResponseBody
    public ApiResult add ( ${entity}  ${entity?uncap_first}){
        ${entity?uncap_first}.setCreateTime(DateUtils.getNowDate());
        ${entity?uncap_first}Service.save(${entity?uncap_first});
        return ApiResult.ok("添加成功!");
    }

    /**
    * 修改${table.comment!?substring(0,2)}信息
    * @param ${entity?uncap_first}
    * @return
    */
    @RequestMapping("update" )
    @ResponseBody
    public ApiResult update(${entity}  ${entity?uncap_first}){
        ${entity?uncap_first}.setUpdateTime(DateUtils.getNowDate());
        ${entity?uncap_first}Service.updateById(${entity?uncap_first});
        return ApiResult.ok("修改成功!");
    }

    /**
    * 批量删除${table.comment!?substring(0,2)}信息
    * @param ${entity?uncap_first}
    * @return
    */
    @RequestMapping("deletes/{${entity?uncap_first}Id}")
    @ResponseBody
    public ApiResult deletes(@PathVariable("${entity?uncap_first}Id") String[] ${entity?uncap_first}Ids){
    ${entity?uncap_first}Service.removeByIds(Arrays.asList(${entity?uncap_first}Ids));
        return new ApiResult("删除成功");
    }


}
</#if>

package ${package.Controller};


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianxing.common.enumeration.ApiResult;
import com.tianxing.common.utils.CheckInformation;
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
    private ${table.serviceName} ${table.serviceName?uncap_first};

    /**
    * 查询${table.comment!?substring(0,2)}列表
    * @return
    */
    @RequestMapping(value = "list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ApiResult list(){
        Page< ${entity}> page = new Page< ${entity}>();
        return new ApiResult(${table.serviceName?uncap_first}.page(page, Wrappers.emptyWrapper()));
    }

    /**
    * 新增${table.comment!?substring(0,2)}信息
    *
    * @param ${entity?uncap_first}
    * @return
    */
    @RequestMapping(value = "add", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ApiResult add ( ${entity}  ${entity?uncap_first}){

        //添加时间
        ${entity?uncap_first}.setCreateTime(DateUtils.getNowDate());

        //校验${table.comment!?substring(0,2)}名是否唯一
        if (CheckInformation.${entity[3..6]?upper_case}_NAME_NOT_UNIQUE.equals(${table.serviceName?uncap_first}.check${entity}Name(${entity?uncap_first}.${"get${entity[3..6]}Name"}()))){
        return new ApiResult("新增${table.comment!?substring(0,2)}"+${entity?uncap_first}.${"get${entity[3..6]}Name"}()+"失败,${table.comment!?substring(0,2)}名已存在");
        }

        //添加${table.comment!?substring(0,2)}信息
        ${table.serviceName?uncap_first}.save(${entity?uncap_first});
        return ApiResult.ok("添加成功!");
    }

    /**
    * 修改${table.comment!?substring(0,2)}信息
    * @param ${entity?uncap_first}
    * @return
    */
    @RequestMapping(value = "update", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ApiResult update(${entity}  ${entity?uncap_first}){

        //修改时间
        ${entity?uncap_first}.setUpdateTime(DateUtils.getNowDate());

        //校验${table.comment!?substring(0,2)}名是否唯一
        if (CheckInformation.${entity[3..6]?upper_case}_NAME_NOT_UNIQUE.equals(${table.serviceName?uncap_first}.check${entity}Name(${entity?uncap_first}.${"get${entity[3..6]}Name"}()))){
        return new ApiResult("修改${table.comment!?substring(0,2)}"+${entity?uncap_first}.${"get${entity[3..6]}Name"}()+"失败,${table.comment!?substring(0,2)}名已存在");
        }

        //修改${table.comment!?substring(0,2)}信息
        ${table.serviceName?uncap_first}.updateById(${entity?uncap_first});
        return ApiResult.ok("修改成功!");
    }

    /**
    * 批量删除${table.comment!?substring(0,2)}信息
    * @param ${entity?uncap_first}Ids
    * @return
    */
    @RequestMapping(value = "deletes/{${entity?uncap_first}Id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ApiResult deletes(@PathVariable("${entity?uncap_first}Id") String[] ${entity?uncap_first}Ids){
        ${table.serviceName?uncap_first}.removeByIds(Arrays.asList(${entity?uncap_first}Ids));
        return new ApiResult("删除成功");
    }


}
</#if>

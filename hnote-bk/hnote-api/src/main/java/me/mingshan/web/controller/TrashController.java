package me.mingshan.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.TrashService;
import me.mingshan.web.model.SearchResultModel;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 12:56 2018/5/23
 */
@Api(value = "trashs")
@RestController
@RequestMapping("/api/trashs")
public class TrashController extends BaseController {

     @Autowired
    private TrashService trashService;

    @Autowired
    private Mapper mapper;

    /**
     * Get all note infos by pagination.
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    @ApiOperation(value="Get all trashs by pagination.", httpMethod="GET", notes="Get trashs")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<SearchResultModel<Note>> listAllNotes(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize);

        PageInfo<Note> pageInfo = trashService.findAll(pageNumber, pageSize);
        List<Note> notes = pageInfo.getList();
        // 总记录数
        Long total = pageInfo.getTotal();
        if(notes.isEmpty()){
            // You many decide to return HttpStatus.NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        SearchResultModel<Note> model = new SearchResultModel<>();
        model.setItems(notes);
        model.setTotal(total);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}

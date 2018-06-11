package me.mingshan.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.TrashService;
import me.mingshan.common.exception.ServerException;
import me.mingshan.common.model.ResultModel;
import me.mingshan.web.vo.SearchResultVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SearchResultVO<Note>> listAllNotes(@RequestParam Integer pageNumber,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam String sort,
                                                             @RequestParam String sortType) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize);

        PageInfo<Note> pageInfo = trashService.findAll(pageNumber, pageSize, sort, sortType);
        List<Note> notes = pageInfo.getList();
        // 总记录数
        Long total = pageInfo.getTotal();
        if(notes.isEmpty()){
            // You many decide to return HttpStatus.NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        SearchResultVO<Note> model = new SearchResultVO<>();
        model.setItems(notes);
        model.setTotal(total);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Recovers note by id.
     * @param:  * @param null
     */
    @RequestMapping(value = "/recover/{id}", method = RequestMethod.PUT)
    @ApiOperation(value="Recover note", httpMethod="PUT", notes="Recover note which is already deleted.")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> deleteNote(@ApiParam(required=true, value="Note ID", name="id")
                                                  @PathVariable("id") Long id) {
        logger.info("Fetching & Deleting Note with id " + id);
        try {
            trashService.recover(id);
        } catch (RuntimeException e) {
            ResultModel result = new ResultModel();
            result.setCode(1024);
            result.setMessage("Unable to recover note with id " + id);
            logger.info("Unable to recover note with id " + id);
            throw new ServerException(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

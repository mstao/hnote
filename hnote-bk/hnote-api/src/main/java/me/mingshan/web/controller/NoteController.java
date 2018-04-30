package me.mingshan.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.NoteService;
import me.mingshan.web.exception.ServerException;
import me.mingshan.web.model.ResultModel;
import me.mingshan.web.model.SearchResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: mingshan
 * @Date: Created in 14:34 2018/4/30
 */
@Api(value = "notes")
@RestController
@RequestMapping("/api/notes")
public class NoteController extends BaseController {
    @Autowired
    private NoteService noteService;

    /**
     * Get all note infos by pagination.
     *
     * @param pageNumber The current page.
     * @param pageSize The page size.
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    @ApiOperation(value="Get all note infos by pagination.", httpMethod="GET", notes="Get notes")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<SearchResultModel<Note>> listAllBooks(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize);

        PageInfo<Note> pageInfo = noteService.findAll(pageNumber, pageSize);
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

    /**
     * Get note by id.
     *
     * @param id The note id.
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="Get note by id", httpMethod="GET", notes="")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.findById(id);
        if (note == null) {
            ResultModel result = new ResultModel();
            result.setCode(1021);
            result.setMessage("Note with id " + id + " not found");
            logger.info("Note with id {} not found", id);
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(note, HttpStatus.OK);
    }
}

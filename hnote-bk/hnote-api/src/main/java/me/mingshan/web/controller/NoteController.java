package me.mingshan.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.NoteService;
import me.mingshan.web.exception.ServerException;
import me.mingshan.web.model.ResultModel;
import me.mingshan.web.model.SearchResultModel;
import me.mingshan.web.vo.NoteVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @Autowired
    private Mapper mapper;

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
    public ResponseEntity<SearchResultModel<Note>> listAllNotes(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize,
                                                                @RequestParam Long nid) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize + "nid = " + nid);

        PageInfo<Note> pageInfo = noteService.findAll(pageNumber, pageSize, nid);
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


    /**
     * Create a note.
     * @param noteVO
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="create a note", httpMethod="POST", notes="Create a note")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<Void> createNote(@ApiParam(required=true, value="笔记信息", name="Note")
                                           @RequestBody NoteVO noteVO, UriComponentsBuilder ucBuilder) {
        Note note = mapper.map(noteVO, Note.class);

        noteService.insert(note);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/notes/{id}").buildAndExpand(note.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Deletes notes by ids.
     * @param:  * @param null
     */
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value="Delete note", httpMethod="DELETE", notes="Delete book by id")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> deleteNote(@ApiParam(required=true, value="Note ID", name="ids")
                                                  @PathVariable("ids") String ids) {
        logger.info("Fetching & Deleting Notes with ids " + ids);
        try {
            noteService.delete(ids);
        } catch (RuntimeException e) {
            ResultModel result = new ResultModel();
            result.setCode(1024);
            result.setMessage("Unable to delete notes with ids " + ids);
            logger.info("Unable to delete notes with ids " + ids);
            throw new ServerException(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

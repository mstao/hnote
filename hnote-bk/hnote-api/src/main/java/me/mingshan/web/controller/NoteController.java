package me.mingshan.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.NoteService;
import me.mingshan.web.exception.ServerException;
import me.mingshan.web.model.ResultModel;
import me.mingshan.web.model.SearchResultModel;
import me.mingshan.web.vo.CreateNoteVO;
import me.mingshan.web.vo.CreatedNoteVO;
import me.mingshan.web.vo.NoteVO;
import me.mingshan.web.vo.UpdateNoteVo;
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
     * @param pageNumber
     * @param pageSize
     * @param fid
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    @ApiOperation(value="Get all notes by pagination.", httpMethod="GET", notes="Get notes")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<SearchResultModel<Note>> listAllNotes(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize,
                                                                @RequestParam Long fid) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize + "fid = " + fid);

        PageInfo<Note> pageInfo = noteService.findAll(pageNumber, pageSize, fid);
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
     * Get the lastest notes.
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/lastest", method = RequestMethod.GET)
    @ApiOperation(value="Get the lastest notes by pagination.", httpMethod="GET", notes="Get notes")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<SearchResultModel<Note>> listLastestNotes(@RequestParam Integer pageNumber,
                                                                   @RequestParam Integer pageSize) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize);

        PageInfo<Note> pageInfo = noteService.findLastestNotes(pageNumber, pageSize);
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
     * Get notes by fuzzy search.
     *
     * @param pageNumber
     * @param pageSize
     * @param token
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value="Get notes by fuzzy search.", httpMethod="GET", notes="Get notes")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<SearchResultModel<Note>> listNotesByFuzzySearch(@RequestParam Integer pageNumber,
                                                                          @RequestParam Integer pageSize,
                                                                          @RequestParam String token) {
        logger.info("page = " + pageNumber + "per_page = " + pageSize);

        PageInfo<Note> pageInfo = noteService.findByToken(token, pageNumber, pageSize);
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
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="create a note", httpMethod="POST", notes="Create a note")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<CreatedNoteVO> createNote(@ApiParam(required=true, value="笔记信息", name="Note")
                                                    @RequestBody CreateNoteVO noteVO) {
        Note note = mapper.map(noteVO, Note.class);
        logger.info("Create note: {}", note);
        Long id = noteService.insert(note);
        CreatedNoteVO vo = new CreatedNoteVO();
        vo.setId(id);
        return new ResponseEntity<>(vo, HttpStatus.CREATED);
    }

    /**
     * Deletes note by id.
     * @param:  * @param null
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value="Delete note", httpMethod="DELETE", notes="Delete book by id")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> deleteNote(@ApiParam(required=true, value="Note ID", name="id")
                                                  @PathVariable("id") Long id) {
        logger.info("Fetching & Deleting Note with id " + id);
        try {
            noteService.delete(id);
        } catch (RuntimeException e) {
            ResultModel result = new ResultModel();
            result.setCode(1024);
            result.setMessage("Unable to delete note with id " + id);
            logger.info("Unable to delete note with id " + id);
            throw new ServerException(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete tag by id.
     * @param:  * @param null
     */
    @RequestMapping(value = "/{id}/tags/{tid}", method = RequestMethod.DELETE)
    @ApiOperation(value="Delete tag", httpMethod="DELETE", notes="Delete tag by id")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> deleteTag(@ApiParam(required=true, value="Note ID", name="id")
                                                  @PathVariable("id") Long id, @PathVariable("tid") Long tid) {
        logger.info("Fetching & Deleting tag with id " + tid);
        try {
            noteService.deleteByNidTid(id, tid);
        } catch (RuntimeException e) {
            ResultModel result = new ResultModel();
            result.setCode(1024);
            result.setMessage("Unable to delete tag with id " + tid);
            logger.info("Unable to delete tag with id " + tid);
            throw new ServerException(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value="Update note", httpMethod="PUT", notes="Update note by id")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> updateNote(@RequestBody UpdateNoteVo updateNoteVo) {
        try {
            Note note = mapper.map(updateNoteVo, Note.class);
            noteService.update(note);
        } catch (RuntimeException e) {
            ResultModel result = new ResultModel();
            result.setCode(1024);
            result.setMessage("Unable to update note: " + updateNoteVo);
            logger.info("Unable to  update note: " + updateNoteVo);
            throw new ServerException(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

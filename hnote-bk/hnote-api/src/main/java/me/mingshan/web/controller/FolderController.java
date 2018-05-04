package me.mingshan.web.controller;

import io.swagger.annotations.*;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Folder;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.FolderService;
import me.mingshan.web.exception.ServerException;
import me.mingshan.web.model.ResultModel;
import me.mingshan.web.vo.FolderVO;
import me.mingshan.web.vo.NoteVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 21:50 2018/4/30
 */
@Api(value = "folders")
@RestController
@RequestMapping("/api/folders")
public class FolderController extends BaseController {
    @Autowired
    private FolderService folderService;

    @Autowired
    private Mapper mapper;

    /**
     * Get folder by id.
     *
     * @param id The folder id.
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="Get folder by id", httpMethod="GET", notes="")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<FolderVO> getNoteById(@PathVariable Long id) {
        Folder folder = folderService.findById(id);
        if (folder == null) {
            ResultModel result = new ResultModel();
            result.setCode(1021);
            result.setMessage("Folder with id " + id + " not found");
            logger.info("Folder with id {} not found", id);
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }

        FolderVO folderVO = mapper.map(folder, FolderVO.class);
        return new ResponseEntity<>(folderVO, HttpStatus.OK);
    }

    /**
     * Get folder by uid.
     *
     * @param uid The folder id.
     * @return
     */
    @RequestMapping(value = "filters", method = RequestMethod.GET)
    @ApiOperation(value="Get folder by uid", httpMethod="GET", notes="")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<List<FolderVO>> getNoteByUid(@RequestParam Long uid) {
        List<FolderVO> folderVOs = new ArrayList<>();
        List<Folder> folders = folderService.findAllByUid(uid);
        for (Folder folder : folders) {
            FolderVO folderVO = mapper.map(folder, FolderVO.class);
            folderVOs.add(folderVO);
        }

        return new ResponseEntity<>(folderVOs, HttpStatus.OK);
    }

    /**
     * Create folder.
     *
     * @param folderVO
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="create folder", httpMethod="POST", notes="Create folder")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<Void> createNote(@ApiParam(required=true, value="Folder", name="Folder")
                                           @RequestBody FolderVO folderVO, UriComponentsBuilder ucBuilder) {
        Folder folder = mapper.map(folderVO, Folder.class);

        folderService.insert(folder);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/notes/{id}").buildAndExpand(folder.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    /**
     * Update folder.
     *
     * @param folderVO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value="Update folder", httpMethod="PUT", notes="Update folder")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<Folder> updateFolder(@RequestBody FolderVO folderVO) {
        Folder folder = mapper.map(folderVO, Folder.class);
        logger.info("Updating folder " + folder);

        Folder currentFolder = folderService.findById(folder.getId());

        if (currentFolder == null) {
            ResultModel result = new ResultModel();
            result.setCode(1023);
            result.setMessage("Folder with id " + folder.getId() + " not found");
            logger.info("Folder with id " + folder.getId() + " not found");
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }

        folderService.rename(folder.getId(), folder.getLabel());
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    /**
     * Deletes notes by ids.
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value="Delete folders", httpMethod="DELETE", notes="Delete notes by ids")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> deleteBook(@ApiParam(required=true, value="Note Ids", name="ids")
                                                  @PathVariable("ids") String ids) {
        logger.info("Fetching & Deleting folders with ids " + ids);
        try {
            folderService.delete(ids);
        } catch (RuntimeException e) {
            ResultModel result = new ResultModel();
            result.setCode(1024);
            result.setMessage("Unable to delete folders with ids " + ids);
            logger.info("Unable to delete folders with ids " + ids);
            throw new ServerException(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

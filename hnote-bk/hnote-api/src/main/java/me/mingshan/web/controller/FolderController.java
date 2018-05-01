package me.mingshan.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.Folder;
import me.mingshan.facade.service.FolderService;
import me.mingshan.web.exception.ServerException;
import me.mingshan.web.model.ResultModel;
import me.mingshan.web.vo.FolderVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

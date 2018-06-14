package me.mingshan.hnote.web.controller;

import io.swagger.annotations.*;
import me.mingshan.hnote.common.annotation.Authorization;
import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.common.model.ResultModel;
import me.mingshan.hnote.facade.model.Share;
import me.mingshan.hnote.facade.service.ShareService;
import me.mingshan.hnote.web.vo.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: mingshan
 * @Date: Created in 8:51 2018/6/13
 */
@Api(value = "notes")
@RestController
@RequestMapping("/api/shares")
public class ShareController extends BaseController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="create a share", httpMethod="POST", notes="Create a share")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<CreatedShareVO> createNote(@ApiParam(required=true, value="分享信息", name="Share")
                                                     @RequestBody CreateShareVO shareVO) {
        Share share = mapper.map(shareVO, Share.class);
        String code = shareService.insert(share);

        CreatedShareVO createdShareVO = new CreatedShareVO();
        createdShareVO.setCode(code);
        return new ResponseEntity<>(createdShareVO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    @ApiOperation(value="Get a share by code.", httpMethod="GET", notes="Get a share by code.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ShareVO> getShareByCode(@ApiParam(required=true, value="Code", name="code")
                                                         @PathVariable("code") String code) {
        Share share = shareService.findByCode(code);

        if (share == null) {
            ResultModel result = new ResultModel();
            result.setCode(1021);
            result.setMessage("Share with code " + code + " not found");
            logger.info("Share with code {} not found", code);
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }

        ShareVO shareVO = mapper.map(share, ShareVO.class);

        return new ResponseEntity<>(shareVO, HttpStatus.OK);
    }
}

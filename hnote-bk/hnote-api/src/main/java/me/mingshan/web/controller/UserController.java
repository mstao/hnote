package me.mingshan.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import me.mingshan.common.exception.ServerException;
import me.mingshan.common.model.ResultModel;
import me.mingshan.web.vo.UserVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mingshan
 * @Date: Created in 22:10 2018/5/2
 */
@Api(value = "users")
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    /**
     * Get user by id.
     *
     * @param id The folder id.
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="Get user by id", httpMethod="GET", notes="")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<UserVO> getNoteById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            ResultModel result = new ResultModel();
            result.setCode(1021);
            result.setMessage("User with id " + id + " not found");
            logger.info("User with id {} not found", id);
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }

        UserVO userVO = mapper.map(user, UserVO.class);
        return new ResponseEntity<>(userVO, HttpStatus.OK);
    }
}

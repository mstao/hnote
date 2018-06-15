package me.mingshan.hnote.web.controller;

import io.swagger.annotations.*;
import me.mingshan.hnote.common.annotation.Authorization;
import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.common.model.ResultModel;
import me.mingshan.hnote.facade.model.User;
import me.mingshan.hnote.facade.service.UserService;
import me.mingshan.hnote.web.config.Constants;
import me.mingshan.hnote.web.util.MD5Util;
import me.mingshan.hnote.web.vo.CreateUserVO;
import me.mingshan.hnote.web.vo.UserVO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<UserVO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            ResultModel result = new ResultModel();
            result.setCode(1001);
            result.setMessage("User with id " + id + " not found");
            logger.info("User with id {} not found", id);
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }

        UserVO userVO = mapper.map(user, UserVO.class);

        return new ResponseEntity<>(userVO, HttpStatus.OK);
    }

    /**
     * Check user
     * @param userName
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ApiOperation(value="Check user by username.", httpMethod="GET", notes="")
    public ResponseEntity<ResultModel> checkUser(String userName) {
        User user = userService.findByUserName(userName);
        ResultModel result = new ResultModel();

        if (user == null) {
            result.setCode(1002);
            result.setMessage("This username is ok!");
        } else {
            result.setCode(1003);
            result.setMessage("User is already exist.");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Create user.
     * 
     * @param userVO
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="create user", httpMethod="POST", notes="Create user")
    public ResponseEntity<Void> createUser(@ApiParam(required=true, value="User", name="user")
                                          @RequestBody CreateUserVO userVO, UriComponentsBuilder ucBuilder) {

        User user = mapper.map(userVO, User.class);
        user.setPassword(MD5Util.md5(user.getPassword(), user.getName()));
        user.setSalt(user.getName());
        user.setAvatarUrl(Constants.DEFAULT_AVATAR);

        Long id = userService.insert(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(id).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}

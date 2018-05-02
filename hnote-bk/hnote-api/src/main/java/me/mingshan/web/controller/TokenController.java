package me.mingshan.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.mingshan.common.annotation.Authorization;
import me.mingshan.common.annotation.CurrentUser;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import me.mingshan.web.authorization.manager.TokenManager;
import me.mingshan.web.config.Constants;
import me.mingshan.web.exception.ParameterException;
import me.mingshan.web.exception.ServerException;
import me.mingshan.web.model.ResultModel;
import me.mingshan.web.model.TokenModel;
import me.mingshan.web.util.MD5Util;
import me.mingshan.web.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * The API of token for login and logout.
 * @Author: Minsghan
 * @Date: Created in 23:14 2017/10/14
 */
@Api(value = "tokens")
@RestController
@RequestMapping("/api/tokens")
public class TokenController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    /**
     *
     * @param tokenVO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="登录处理", httpMethod="POST", notes="Login")
    public ResponseEntity<TokenModel> login(@RequestBody TokenVO tokenVO) {
        ResultModel result = new ResultModel();
        String userName = tokenVO.getUserName();
        String password = tokenVO.getPassword();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new ParameterException("UserName or password is empty.");
        }

        User user = userService.findByUserName(userName);

        String newPassword = MD5Util.md5(password, userName);
        if (user == null || !newPassword.equals(user.getPassword())) {
            result.setCode(1001);
            result.setMessage("UserName or password is incorrect.");
            logger.info("UserName or password is incorrect.");
            throw new ServerException(result, HttpStatus.NOT_FOUND);
        }
        
        TokenModel token = tokenManager.creatToken(user.getId());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Logout
     * @param user
     * @return ResultModel
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value="登出处理", httpMethod="DELETE", notes="Logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<ResultModel> logout(@CurrentUser User user) {
        tokenManager.deleteToken(user.getId());
        ResultModel result = new ResultModel();
        result.setMessage(Constants.RESPONSE_OK);
        return new ResponseEntity<ResultModel>(result, HttpStatus.OK);
    }
}

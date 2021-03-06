package me.mingshan.hnote.web.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.mingshan.hnote.common.annotation.Authorization;
import me.mingshan.hnote.facade.model.Note;
import me.mingshan.hnote.facade.service.NoteService;
import me.mingshan.hnote.web.config.Constants;
import me.mingshan.hnote.web.vo.UploadImageVO;
import me.mingshan.hnote.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: mingshan
 * @Date: Created in 10:44 2018/5/25
 */
@Api(value = "files")
@RestController
@RequestMapping("/api/files")
public class FileController extends BaseController {

    @Autowired
    private NoteService noteService;

    /**
     * 上传图片
     *
     * @param file
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ApiOperation(value="Upload image to qiniu.", httpMethod="POST", notes="")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "String",
                    paramType = "header")
    })
    public ResponseEntity<UploadImageVO> uploadPic(MultipartFile file, HttpServletRequest request)
            throws ServletException, IOException {

        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        String fi = file.getOriginalFilename();
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.lastIndexOf("."), fi.length());
        // 生成实际存储的真实文件名
        String key = UUID.randomUUID().toString() + fileNameExtension;
        String url = "";

        try {
            Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRET_KEY);
            String upToken = auth.uploadToken(Constants.QINIU_UPLOAD_TOKEN);
            try {
                Response response = uploadManager.put(file.getInputStream(), key, upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                url = Constants.QINIU_UPLOAD_URL + "/" + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            //ignore
        }

        UploadImageVO imageVO = new UploadImageVO();
        imageVO.setUrl(url);
        return new ResponseEntity<>(imageVO, HttpStatus.OK);
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value="Download file which is md or txt. ", httpMethod="GET", notes="下载文件")
    public void download(@RequestParam Long bid, HttpServletResponse response) {
        Note note = noteService.findById(bid);
        String content = note.getContent();
        String title = note.getTitle();

        String fileName = "";
        if (Constants.MD.equals(note.getNoteType().getName())) {
            fileName = title + ".md";
        } else if (Constants.WORD.equals(note.getNoteType().getName())) {
            fileName = title + ".txt";
        }

        byte[] bytes = content.getBytes();

        // 设置下载响应头
        FileUtil.setFileDownloadHeader(response, fileName);

        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

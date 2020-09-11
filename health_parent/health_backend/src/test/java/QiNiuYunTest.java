import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author LLaamar
 * @date 2020/9/9 11:23
 */
public class QiNiuYunTest {
    private final String ACCESSKEY = "oIrzXHFHTK2R63YehhgKAqwvtjyL3Pwv8N6sT46l";
    private final String SECRETKEY = "ibYDt3QOfFiMq6chEqSjf2iSiKXBnn4h4UeoFE_e";
    private final String BUCKET = "llaamar-health-01";

    /**
     * 测试文件上传
     */
//    @Test
   public void fileUploadTest(){
       //构造一个带指定Zone对象的配置类
       Configuration cfg = new Configuration(Zone.zone0());
       //...其他参数参考类注释
       UploadManager uploadManager = new UploadManager(cfg);
       //...生成上传凭证，然后准备上传
//        String accessKey = "oIrzXHFHTK2R63YehhgKAqwvtjyL3Pwv8N6sT46l"; //七牛云AccessKey
//        String secretKey = "ibYDt3QOfFiMq6chEqSjf2iSiKXBnn4h4UeoFE_e"; //七牛云SecretKey
//        String bucket = "llaamar-health-01"; //七牛云存储空间

        String accessKey = ACCESSKEY; //七牛云AccessKey
        String secretKey = SECRETKEY; //七牛云SecretKey
        String bucket = BUCKET; //七牛云存储空间
       //如果是Windows情况下，格式是 D:\\qiniu\\test.png
       String localFilePath = "D:\\DevelopFile\\传智健康2.0\\day04\\资源\\图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg";
       //默认不指定key的情况下，以文件内容的hash值作为文件名
       String key = null; // 这行代码是用来定义文件名的
//       String key = "file_upload_test.jpg"; // 这行代码是用来定义文件名的
       Auth auth = Auth.create(accessKey, secretKey);
       String upToken = auth.uploadToken(bucket);
       try {
           Response response = uploadManager.put(localFilePath, key, upToken);
           //解析上传成功的结果
           DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
           System.out.println(putRet.key);
           System.out.println(putRet.hash);
       } catch (QiniuException ex) {
           Response r = ex.response;
           System.err.println(r.toString());
           try {
               System.err.println(r.bodyString());
           } catch (QiniuException ex2) {
               //ignore
           }
       }
   }

//   @Test
   public void fileDeleteTest(){
       //构造一个带指定Zone对象的配置类
       Configuration cfg = new Configuration(Zone.zone0());

       //...其他参数参考类注释
//       String accessKey = "your access key";
//       String secretKey = "your secret key";
//       String bucket = "your bucket name";

       String accessKey = ACCESSKEY; //七牛云AccessKey
       String secretKey = SECRETKEY; //七牛云SecretKey
       String bucket = BUCKET; //七牛云存储空间

       String key = "FuM1Sa5TtL_ekLsdkYWcf5pyjKGu";

       Auth auth = Auth.create(accessKey, secretKey);
       BucketManager bucketManager = new BucketManager(auth, cfg);
       try {
           bucketManager.delete(bucket, key);
       } catch (QiniuException ex) {
           //如果遇到异常，说明删除失败
           System.err.println(ex.code());
           System.err.println(ex.response.toString());
       }
   }

//   @Test
   public void testString(){
        String fileName = "01234.bb";
        int lastIndexOf = fileName.lastIndexOf(".");
        System.out.println("================");
        System.out.println(lastIndexOf);
        System.out.println("================");

        String suffix = fileName.substring(lastIndexOf - 1);
        System.out.println(suffix);
       System.out.println("================");
   }
}

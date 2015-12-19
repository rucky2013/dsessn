package com.github.dsessn.security.oauth;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 身份验证令牌生成器。
 * 用于单点登录系统通过URL自动登录时，对URL上的用户名和密码进行加密。
 *
 * @author pinian.lpn
 */
public class OauthTokenBuilder {

    protected final static Log logger = LogFactory.getLog(OauthTokenBuilder.class);

    private static final String[] SPLITS = new String[]{"||", "^^", "**", "$$", "++"};
    private static final String[] SPLITS_REG = new String[]{"\\|\\|", "\\^\\^", "\\*\\*", "\\$\\$", "\\+\\+"};
    private static String ENCRYPT_KEY = "k@d@ngd0tcom";

    private volatile static OauthTokenBuilder instance;
    private DESedeEncryptor desEncrypt = new DESedeEncryptor();

    public static OauthTokenBuilder get() {
        if (instance == null) {
            synchronized (OauthTokenBuilder.class) {
                if (instance == null)
                    instance = new OauthTokenBuilder();
            }
        }
        return instance;
    }

    /**
     * 加密 Token对象
     * 如果加密后的加密串要用于 URL 进行传递，那么就必须
     *
     * @param token OauthToken对象
     * @return 返回token字符串
     */
    public String build(OauthToken token) {
        StringBuffer sb = new StringBuffer();
        String split = this.findSplit(StringUtils.join(new String[]{
                token.getUsername(), token.getPassword()}));
        sb.append(System.currentTimeMillis()).append(split)
                .append(token.getUsername()).append(split)
                .append(token.getPassword());

        return desEncrypt.encrypt(sb.toString(), ENCRYPT_KEY);
    }

    /**
     * 根据 token 串解密出 OauthToken 对象
     *
     * @param token token字符串
     * @return 返回OauthToken对象
     */
    public OauthToken parse(String token) {
        String deToken = desEncrypt.decrypt(token, ENCRYPT_KEY);
        if (deToken == null)
            return null;
        String[] tokens = this.findSplitsToken(deToken);
        if (tokens != null && tokens.length == 3) {
            OauthToken oauthToken = new OauthToken();
            oauthToken.setUsername(tokens[1]);
            oauthToken.setPassword(tokens[2]);
            try {// token时间限制，半小时
                long time = Long.parseLong(tokens[0]);
                if ((System.currentTimeMillis() - time) > 1800000) {
                    logger.error("令牌已超时");
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
            return oauthToken;
        } else {
            logger.error("令牌已超时");
        }
        return null;
    }

    // 查找出合适的分隔符
    private String findSplit(String name) {
        for (String split : SPLITS) {
            if (name.indexOf(split) < 0)
                return split;
        }
        return SPLITS[0];
    }

    // 同样，反解析时，也需要查找到合适的分隔符
    private String[] findSplitsToken(String deToken) {
        String[] tokens;
        for (String reg : SPLITS_REG) {
            tokens = deToken.split(reg);
            if (tokens.length == 3)
                return tokens;
        }
        return null;
    }


//	public static void main(String[] args) throws Exception {
//		OauthToken oauthToken = new OauthToken();
//		oauthToken.setUsername("swingfasdfadsf");
//		oauthToken.setPassword("abcde");
//        List<String> tokens = new ArrayList<String>();
//        for (int i = 0; i < 10; i++) {
//			String token = OauthTokenBuilder.get().build(oauthToken);
//			System.out.println(token + " |||| "
//					+ URLEncoder.encode(token, "UTF-8"));
//			tokens.add(token);
//			Thread.sleep(10);
//		}
//		for (String token : tokens) {
//			OauthToken t = OauthTokenBuilder.get()
//					.parse(token);
//			System.out.print(t.getUsername() + " --- ");
//			System.out.println(t.getPassword());
//		}
//	}
}

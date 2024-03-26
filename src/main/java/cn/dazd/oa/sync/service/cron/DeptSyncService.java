package cn.dazd.oa.sync.service.cron;

import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeptSyncService {
    public synchronized void doTask() {
        new AbsCronService(DeptSyncService.class) {
            @Override
            public void doTask() {
                PropConfig config = new PropConfig();
                log.info("{} running", DeptSyncService.class.getName());
                int responseStatus = HttpUtil.createPost(config.getUrl())
                        .form("type", 1)
                        .header("Cookie", config.getCookies()).execute().getStatus();
                log.info("分部同步完成，接口调用状态：" + responseStatus);
            }
        }.run();
    }

    @EqualsAndHashCode
    @Data
    @ToString
    public static class PropConfig {
        private String url = "https://oa.dazd.cn/HrSyn/HRManualSynBytime.jsp";
        private String cookies = "experimentation_subject_id=IjA2ZDVlYTUxLWI2MzAtNDFmNy05NGRhLTc5YjQxMGQ3MjRkZCI%3D--6858c1555294cbb56f04ed88dfe3018244a3158e; ecology_JSessionid=aaayMMoN5-hJS7Reb3c5y; JSESSIONID=aaayMMoN5-hJS7Reb3c5y; Systemlanguid=7; languageidweaver=7; loginuuids=1; online_ticket=OT-EMDTpy6uIlKuKvLSQfI7p; tfstk=fm39u5Yx6pvGSiYO4mtn0e0l2PAHrVha9Al5o-2GcvHKnbQmnj94knMzKVq05h7bGAlgCVxaIvdxdYlgmqhVkSGj3CyD_HcZ_rzXEL4vrflw1v-iJp4XGXsyBVyBrUcZ1MBPsfYk_dip0uwbllZ1dywUZ5abcROLdSFPcOMblXDyC6SDOa0oX5tr9WC6QxkL6leRoJQAkIVTX8G_pH-EJZZTFfwdlLLYbheYL49HvXkIfAVEHUpLuvojhSHCdKqZGDHL6AvR3-0Zsqei6h9Z9ywtGoHvHiFtqAH4Cf61f7UTBug_Y_dj9oiKJP063gln9RZ8-DxeIo4tB0yzfHJEHXeihVECBgRKr4E2zT28iGOp9iS4fWWVjSeP7ZzxIWek1lsV0oVU98Ap9iS4fWPLEC_f0ir0T; loginidweaver=sysadmin; __randcode__=bad3cbbc-fa6a-4722-84cb-729bf3f75fed";
    }

}

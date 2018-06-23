package leftovers.controller;

import com.alibaba.fastjson.JSONObject;
import leftovers.enums.DefaultAlgorithmInfo;
import leftovers.model.Algorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Hiki on 2017/6/9.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AlgorithmControllerTest {

    @Autowired
    AlgorithmController tested;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(tested).build();
    }

    String code =
            "\n" +
            "\n" +
            "# 在这个方法中编写任何的初始化逻辑。context对象将会在你的算法策略的任何方法之间做传递。\n" +
            "def init(context):\n" +
            "    logger.info(\"init\")\n" +
            "    context.s1 = \"000001.XSHE\"\n" +
            "    update_universe(context.s1)\n" +
            "    # 是否已发送了order\n" +
            "    context.fired = False\n" +
            "\n" +
            "\n" +
            "def before_trading(context):\n" +
            "    pass\n" +
            "\n" +
            "\n" +
            "# 你选择的证券的数据更新将会触发此段逻辑，例如日或分钟历史数据切片或者是实时数据切片更新\n" +
            "def handle_bar(context, bar_dict):\n" +
            "    # 开始编写你的主要的算法逻辑\n" +
            "\n" +
            "    # bar_dict[order_book_id] 可以拿到某个证券的bar信息\n" +
            "    # context.portfolio 可以拿到现在的投资组合状态信息\n" +
            "\n" +
            "    # 使用order_shares(id_or_ins, amount)方法进行落单\n" +
            "\n" +
            "    # TODO: 开始编写你的算法吧！\n" +
            "    if not context.fired:\n" +
            "        # order_percent并且传入1代表买入该股票并且使其占有投资组合的100%\n" +
            "        order_percent(context.s1, 1)\n" +
            "        context.fired = True\n";


    String algoId = "example24";
    String algoName = "ex";
    String username = "aneureka";
    String time = LocalDateTime.now().toString();
    String beginDate = "2016-06-01";
    String endDate = "2016-12-01";
    double stockStartCash = 100000;
    String benchmark = "000300.XSHG";

    Algorithm algorithm = new Algorithm(algoId, algoName, username, time, code, beginDate, endDate, stockStartCash, benchmark);

    @Test
    public void createAlgorithm() throws Exception {

        mockMvc.perform(post("/api/algorithm/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(algorithm)))
                .andDo(print());

    }



    @Test
    public void updateAlgorithm() throws Exception {

        System.out.println(JSONObject.toJSONString(algorithm));
        mockMvc.perform(post("/api/algorithm/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(algorithm)))
                .andDo(print());
    }
    
    @Test
    public void createDefault() throws Exception {

        Algorithm algorithmd = new Algorithm("dwada", algorithm.getAlgoName(), algorithm.getUsername(), algorithm.getTime(), DefaultAlgorithmInfo.CODE_PREFIX + DefaultAlgorithmInfo.CODE, DefaultAlgorithmInfo.BEGIN_DATE, DefaultAlgorithmInfo.END_DATE, DefaultAlgorithmInfo.STOCK_START_CASH, DefaultAlgorithmInfo.BENCHMARK);

        mockMvc.perform(post("/api/algorithm/createDefault")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(algorithmd)))
                .andDo(print());
        
    }
    
    
    
    

    @Test
    public void removeAlgorithm() throws Exception {

        mockMvc.perform(get("/api/algorithm/remove")
                .param("algoId", algoId))
                .andDo(print());

    }

    @Test
    public void findAlgorithmById() throws Exception {
        mockMvc.perform(get("/api/algorithm/findAlgorithmById")
                .param("algoId", algoId))
                .andDo(print());

    }

    @Test
    public void findAlgorithmByUsername() throws Exception {
    }

}
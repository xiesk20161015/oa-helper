package cn.dazd.oa.sync.contronller;

import cn.dazd.oa.sync.dto.MatrixListPageDTO;
import cn.dazd.oa.sync.response.AjaxPagerResponse;
import cn.dazd.oa.sync.service.EcoMatrixAndWorkflowService;
import cn.dazd.oa.sync.vo.EcoMatrixAndWorkflowVO;
import cn.dazd.oa.sync.vo.EcoMatrixFieldVO;
import cn.dazd.oa.sync.vo.EcoMatrixVO;
import cn.dazd.oa.sync.vo.PagerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class EcoMatrixController {
    private final EcoMatrixAndWorkflowService ecoMatrixAndWorkflowService;

    public EcoMatrixController(EcoMatrixAndWorkflowService ecoMatrixAndWorkflowService) {
        this.ecoMatrixAndWorkflowService = ecoMatrixAndWorkflowService;
    }

    @PostMapping("/eco_matrix_and_node/list")
    @ResponseBody
    public AjaxPagerResponse<EcoMatrixAndWorkflowVO> getMatrixAndNodeListPage(MatrixListPageDTO matrixListPageDTO) {
        return AjaxPagerResponse.cons(ajax -> {
            PagerVO<EcoMatrixAndWorkflowVO> pagerVO = ecoMatrixAndWorkflowService.getMatrixAndNodeListPage(matrixListPageDTO);
            ajax.setRows(pagerVO.getRows());
            ajax.setTotal(pagerVO.getTotal());
            ajax.setPages(matrixListPageDTO.getPage());
        });
    }

    @PostMapping("/eco_matrix/matrix/list")
    @ResponseBody
    public List<EcoMatrixVO> getMatrixList() {
        return ecoMatrixAndWorkflowService.getMatrixList();
    }

    @PostMapping("/eco_matrix/matrix_field_{matrixInfoId}/list")
    @ResponseBody
    public List<EcoMatrixFieldVO> getMatrixFeildList(@PathVariable Integer matrixInfoId) {
        return ecoMatrixAndWorkflowService.getMatrixFeildList(matrixInfoId);
    }
}

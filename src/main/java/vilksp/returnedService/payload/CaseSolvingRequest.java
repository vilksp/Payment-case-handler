package vilksp.returnedService.payload;

import vilksp.returnedService.model.ResolutionStatus;

public class CaseSolvingRequest {
    private Long caseId;
    private ResolutionStatus status;

    public Long getCaseId() {
        return caseId;
    }

    public ResolutionStatus getStatus() {
        return status;
    }

    public CaseSolvingRequest(Long caseId, ResolutionStatus status) {
        this.caseId = caseId;
        this.status = status;
    }
}

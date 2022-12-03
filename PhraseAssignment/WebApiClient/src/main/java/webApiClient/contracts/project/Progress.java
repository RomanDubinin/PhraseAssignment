package webApiClient.contracts.project;

import lombok.Getter;

@Getter
public class Progress {
    private int overdueCount;
    private int totalCount;
    private int finishedCount;
}

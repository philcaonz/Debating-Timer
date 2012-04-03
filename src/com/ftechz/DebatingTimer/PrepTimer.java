package com.ftechz.DebatingTimer;

/**
 * SpeakerTimer class
 * Exist as a stage in a debate, keeping the timer of the stage
 * and its own internal state according on the AlarmChainAlerts provided
 */
public class PrepTimer extends AlarmChain
{
    enum PrepState {
        Setup,
        ChooseMoot,
        ChooseSide,
        Prepare,
        Finish
    }

    private PrepState mPrepState = PrepState.Setup;

    public PrepTimer(AlarmChainAlert alarmChainAlert[])
    {
        super(alarmChainAlert, true);
    }

    @Override
    protected void handleAlert(AlarmChainAlert alert) {
        Class alertClass = alert.getClass();

        if(alertClass == IntermediateAlert.class)
        {
            // Do nothing
        }
        else if(alertClass == WarningAlert.class)
        {
            if(mPrepState == PrepState.ChooseMoot)
            {
                mPrepState = PrepState.ChooseSide;
            }
            else if(mPrepState == PrepState.ChooseSide)
            {
                mPrepState = PrepState.Prepare;
            }
        }
        else if(alertClass == FinishAlert.class)
        {
            mPrepState = PrepState.Finish;
        }
        else if(alertClass == OvertimeAlert.class)
        {
            // Do nothing
        }

        alert.alert();
    }

    @Override
    public String getNotificationText() {
        return String.format("Stage: %s", getStateText());
    }

    public String getStateText()
    {
        String text = "";
        switch(mPrepState)
        {
            case Setup:
                text = "Setup";
                break;
            case ChooseMoot:
                text = "Choose moot";
                break;
            case ChooseSide:
                text = "Choose side";
                break;
            case Prepare:
                text = "Prepare";
                break;
            case Finish:
                text = "Preparation finish";
                break;
            default:
                break;
        }
        return text;
    }

    @Override
    public String getTitleText() {
        return "Preparation";
    }

    @Override
    protected void onStart() {
        mPrepState = PrepState.ChooseMoot;
    }

    @Override
    public PrepTimer newCopy()
    {
        return new PrepTimer(mAlerts.toArray(new AlarmChainAlert[mAlerts.size()]));
    }
}

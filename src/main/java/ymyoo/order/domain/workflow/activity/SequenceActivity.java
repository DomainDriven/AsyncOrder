package ymyoo.order.domain.workflow.activity;

/**
 * Created by 유영모 on 2016-11-08.
 */
public interface SequenceActivity<T> {
    T perform();
}

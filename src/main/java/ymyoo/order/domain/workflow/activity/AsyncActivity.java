package ymyoo.order.domain.workflow.activity;

/**
 * Created by 유영모 on 2016-11-24.
 */
public interface AsyncActivity<T, R>{
    void perform();
    R callback(T t);
}

package ymyoo.app.order.domain.workflow.activity;

import ymyoo.messaging.core.Callback;

/**
 * Created by 유영모 on 2017-01-23.
 */
public interface AsyncBusinessActivity<T, C> {
    void perform(T t, Callback<C> callback);
    String getId();
}

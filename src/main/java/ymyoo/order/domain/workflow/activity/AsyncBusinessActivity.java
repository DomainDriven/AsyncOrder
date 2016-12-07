package ymyoo.order.domain.workflow.activity;

import ymyoo.infra.messaging.remote.channel.Callback;

/**
 * Created by 유영모 on 2016-11-24.
 */
public interface AsyncBusinessActivity<T, C>{
    void perform(T t, Callback<C> callback);
    String getId();
}

package ymyoo.app.order.domain.workflow.activity;

/**
 * Created by 유영모 on 2017-01-23.
 */
public interface SyncBusinessActivity<T, R> {
    R perform(T t);
}

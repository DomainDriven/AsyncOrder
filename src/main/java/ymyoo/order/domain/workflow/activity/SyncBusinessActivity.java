package ymyoo.order.domain.workflow.activity;

/**
 * Created by 유영모 on 2016-12-07.
 */
public interface SyncBusinessActivity <T, R> {
    R perform(T t);
}

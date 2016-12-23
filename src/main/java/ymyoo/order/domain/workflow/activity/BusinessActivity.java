package ymyoo.order.domain.workflow.activity;

/**
 * Created by 유영모 on 2016-12-23.
 */
public interface BusinessActivity <T, R> {
    R perform(T t);
}

package ymyoo.domain.order.workflow;

/**
 * Created by 유영모 on 2016-10-24.
 */
public interface OrderProcess<T> {
    void runWorkflow(T order);
}

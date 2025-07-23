package ryzendee.app.exception;

/**
 * Исключение, выбрасываемое в бизнес-логике при отсутствии требуемого ресурса или объекта.
 *
 * Используется для информирования вызывающего кода о том, что запрошенный ресурс не существует.
 *
 * @author Dmitry Ryazantsev
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

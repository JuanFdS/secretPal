package persistence;
import model.Participant;
import java.util.ArrayList;
import java.util.List;


public class InMemoryParticipantDao implements AbstractRepository<Participant> {

    protected static List<Participant> participants = new ArrayList<Participant>();

    public static List<Participant> getParticipants() {
        return participants;
    }

    public static void setParticipants(List<Participant> participants) {
        InMemoryParticipantDao.participants = participants;
    }

    @Override
    public List<Participant> retrieveAll() {
        return participants;
    }

    @Override
    public void save(Participant element) {
        participants.add(element);
    }
}

package org.isec.pa.ecossistema;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.LookingForFloraState;
import org.isec.pa.ecossistema.utils.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LookingForFloraStateTest {

    private FaunaContext context;
    private Fauna fauna;
    private LookingForFloraState lookingForFloraState;

    @BeforeEach
    public void setUp() {
        context = mock(FaunaContext.class);
        fauna = mock(Fauna.class);
        lookingForFloraState = new LookingForFloraState(context, fauna);
    }

    @Test
    public void testEvolve_FaunaNotAlive() {
        when(fauna.checkIfAlive()).thenReturn(false);

        lookingForFloraState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }

    @Test
    public void testEvolve_FaunaOnFloraAndForcaLessThan80() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(true);
        when(fauna.getForca()).thenReturn(70.0);

        lookingForFloraState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).checkIfOnFlora();
        verify(fauna, times(1)).getForca();
        verify(context, times(1)).changeState(any(FaunaState.EATING.getInstance(context, fauna).getClass()));
    }

    @Test
    public void testEvolve_FaunaHasTarget() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(false);
        Area targetFauna = new Area(0, 0, 0, 0);
        when(fauna.getTarget()).thenReturn(targetFauna);

        lookingForFloraState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).checkIfOnFlora();
        verify(fauna, times(1)).getTarget();
        verify(fauna, times(1)).getDirectionOfTarget();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }

    @Test
    public void testEvolve_FaunaNoTarget_FoundFlora() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(false);
        when(fauna.getTarget()).thenReturn(null);
        Area targetFlora = new Area(0, 0, 0, 0);
        when(fauna.checkForAdjacentFlora()).thenReturn(targetFlora);
        when(fauna.getForca()).thenReturn(70.0);

        lookingForFloraState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).checkIfOnFlora();
        verify(fauna, times(1)).getTarget();
        verify(fauna, times(1)).checkForAdjacentFlora();
        verify(fauna, times(1)).setTarget(targetFlora);
        verify(fauna, times(1)).getDirectionOfTarget();
        verifyNoInteractions(context);
    }

    @Test
    public void testEvolve_FaunaNoTarget_NoFloraFound_FoundFauna() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(false);
        when(fauna.getTarget()).thenReturn(null);
        when(fauna.checkForAdjacentFlora()).thenReturn(null);
        Area targetFauna = new Area(0, 0, 0, 0);
        when(fauna.lookForWeakestFauna()).thenReturn(targetFauna);

        lookingForFloraState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).checkIfOnFlora();
        verify(fauna, times(1)).getTarget();
        verify(fauna, times(1)).checkForAdjacentFlora();
        verify(fauna, times(1)).lookForWeakestFauna();
        verify(context, times(1)).changeState(any(FaunaState.LOOKING_FOR_FAUNA.getInstance(context, fauna).getClass()));
        verifyNoMoreInteractions(fauna);
    }

    @Test
    public void testEvolve_FaunaNoTarget_NoFloraFound_NoFaunaFound() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(false);
        when(fauna.getTarget()).thenReturn(null);
        when(fauna.checkForAdjacentFlora()).thenReturn(null);
        when(fauna.lookForWeakestFauna()).thenReturn(null);

        lookingForFloraState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).checkIfOnFlora();
        verify(fauna, times(1)).getTarget();
        verify(fauna, times(1)).checkForAdjacentFlora();
        verify(fauna, times(1)).lookForWeakestFauna();
        verify(fauna, times(1)).setDirection(null);
        verify(fauna, times(1)).setTarget(null);
        verify(fauna, times(1)).getDirectionOfTarget();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }
}

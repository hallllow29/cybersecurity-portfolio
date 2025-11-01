-- Gerar alerta atraso na OrdemFabrico
CREATE TRIGGER tr_OrdemFabrico_GerarAlertaAtraso
ON OrdemFabrico
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO AlertaAtraso(ordemID, dataGeracao, motivo)
    SELECT
        i.ordemID,
        SYSDATETIME(),
        'A ordem ultrapassou o prazo de conclusão'
    FROM inserted i

     -- prazo ultrapassado e ainda nao esta concluida a ordem se nao existir faz alerta, evitando duplicação de alerta
    WHERE 
        i.dataPrevistaConclusao < SYSDATETIME() AND i.estado <> 'concluida'
        AND NOT EXISTS (
            SELECT 1
            FROM AlertaAtraso a
            WHERE a.ordemID = i.ordemID
        );
END;

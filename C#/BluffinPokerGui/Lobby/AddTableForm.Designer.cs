namespace BluffinPokerGUI.Lobby
{
    partial class AddTableForm
    {
        /// <summary>
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur Windows Form

        /// <summary>
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblTableName = new System.Windows.Forms.Label();
            this.txtTableName = new System.Windows.Forms.TextBox();
            this.lblGameLimit = new System.Windows.Forms.Label();
            this.clstGameLimit = new System.Windows.Forms.ComboBox();
            this.lblBigBlindAmnt = new System.Windows.Forms.Label();
            this.nudBigBlindAmnt = new System.Windows.Forms.NumericUpDown();
            this.nudNbPlayers = new System.Windows.Forms.NumericUpDown();
            this.lblNbPlayers = new System.Windows.Forms.Label();
            this.grpWTA = new System.Windows.Forms.GroupBox();
            this.nudWTAPotWon = new System.Windows.Forms.NumericUpDown();
            this.lblWTAPotWon = new System.Windows.Forms.Label();
            this.nudWTABoardDealed = new System.Windows.Forms.NumericUpDown();
            this.lblWTABoardDealed = new System.Windows.Forms.Label();
            this.nudWTAPlayerAction = new System.Windows.Forms.NumericUpDown();
            this.lblWTAPlayerAction = new System.Windows.Forms.Label();
            this.btnAdd = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.nudBigBlindAmnt)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayers)).BeginInit();
            this.grpWTA.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPotWon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTABoardDealed)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPlayerAction)).BeginInit();
            this.SuspendLayout();
            // 
            // lblTableName
            // 
            this.lblTableName.AutoSize = true;
            this.lblTableName.Location = new System.Drawing.Point(12, 9);
            this.lblTableName.Name = "lblTableName";
            this.lblTableName.Size = new System.Drawing.Size(68, 13);
            this.lblTableName.TabIndex = 0;
            this.lblTableName.Text = "Table Name:";
            // 
            // txtTableName
            // 
            this.txtTableName.Location = new System.Drawing.Point(15, 25);
            this.txtTableName.Name = "txtTableName";
            this.txtTableName.Size = new System.Drawing.Size(201, 20);
            this.txtTableName.TabIndex = 1;
            // 
            // lblGameLimit
            // 
            this.lblGameLimit.AutoSize = true;
            this.lblGameLimit.Location = new System.Drawing.Point(12, 48);
            this.lblGameLimit.Name = "lblGameLimit";
            this.lblGameLimit.Size = new System.Drawing.Size(62, 13);
            this.lblGameLimit.TabIndex = 1;
            this.lblGameLimit.Text = "Game Limit:";
            // 
            // clstGameLimit
            // 
            this.clstGameLimit.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.clstGameLimit.FormattingEnabled = true;
            this.clstGameLimit.Location = new System.Drawing.Point(15, 64);
            this.clstGameLimit.Name = "clstGameLimit";
            this.clstGameLimit.Size = new System.Drawing.Size(201, 21);
            this.clstGameLimit.TabIndex = 3;
            // 
            // lblBigBlindAmnt
            // 
            this.lblBigBlindAmnt.AutoSize = true;
            this.lblBigBlindAmnt.Location = new System.Drawing.Point(219, 49);
            this.lblBigBlindAmnt.Name = "lblBigBlindAmnt";
            this.lblBigBlindAmnt.Size = new System.Drawing.Size(90, 13);
            this.lblBigBlindAmnt.TabIndex = 4;
            this.lblBigBlindAmnt.Text = "Big Blind Amount:";
            // 
            // nudBigBlindAmnt
            // 
            this.nudBigBlindAmnt.Increment = new decimal(new int[] {
            5,
            0,
            0,
            0});
            this.nudBigBlindAmnt.Location = new System.Drawing.Point(222, 65);
            this.nudBigBlindAmnt.Maximum = new decimal(new int[] {
            1000,
            0,
            0,
            0});
            this.nudBigBlindAmnt.Minimum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.nudBigBlindAmnt.Name = "nudBigBlindAmnt";
            this.nudBigBlindAmnt.Size = new System.Drawing.Size(84, 20);
            this.nudBigBlindAmnt.TabIndex = 4;
            this.nudBigBlindAmnt.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            // 
            // nudNbPlayers
            // 
            this.nudNbPlayers.Location = new System.Drawing.Point(222, 25);
            this.nudNbPlayers.Maximum = new decimal(new int[] {
            9,
            0,
            0,
            0});
            this.nudNbPlayers.Minimum = new decimal(new int[] {
            2,
            0,
            0,
            0});
            this.nudNbPlayers.Name = "nudNbPlayers";
            this.nudNbPlayers.Size = new System.Drawing.Size(84, 20);
            this.nudNbPlayers.TabIndex = 2;
            this.nudNbPlayers.Value = new decimal(new int[] {
            9,
            0,
            0,
            0});
            // 
            // lblNbPlayers
            // 
            this.lblNbPlayers.AutoSize = true;
            this.lblNbPlayers.Location = new System.Drawing.Point(219, 9);
            this.lblNbPlayers.Name = "lblNbPlayers";
            this.lblNbPlayers.Size = new System.Drawing.Size(73, 13);
            this.lblNbPlayers.TabIndex = 6;
            this.lblNbPlayers.Text = "Nb of Players:";
            // 
            // grpWTA
            // 
            this.grpWTA.Controls.Add(this.nudWTAPotWon);
            this.grpWTA.Controls.Add(this.lblWTAPotWon);
            this.grpWTA.Controls.Add(this.nudWTABoardDealed);
            this.grpWTA.Controls.Add(this.lblWTABoardDealed);
            this.grpWTA.Controls.Add(this.nudWTAPlayerAction);
            this.grpWTA.Controls.Add(this.lblWTAPlayerAction);
            this.grpWTA.Location = new System.Drawing.Point(15, 91);
            this.grpWTA.Name = "grpWTA";
            this.grpWTA.Size = new System.Drawing.Size(291, 70);
            this.grpWTA.TabIndex = 8;
            this.grpWTA.TabStop = false;
            this.grpWTA.Text = "Waiting time after ... (ms)";
            // 
            // nudWTAPotWon
            // 
            this.nudWTAPotWon.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTAPotWon.Location = new System.Drawing.Point(198, 38);
            this.nudWTAPotWon.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTAPotWon.Name = "nudWTAPotWon";
            this.nudWTAPotWon.Size = new System.Drawing.Size(84, 20);
            this.nudWTAPotWon.TabIndex = 7;
            this.nudWTAPotWon.Value = new decimal(new int[] {
            2500,
            0,
            0,
            0});
            // 
            // lblWTAPotWon
            // 
            this.lblWTAPotWon.AutoSize = true;
            this.lblWTAPotWon.Location = new System.Drawing.Point(195, 22);
            this.lblWTAPotWon.Name = "lblWTAPotWon";
            this.lblWTAPotWon.Size = new System.Drawing.Size(52, 13);
            this.lblWTAPotWon.TabIndex = 10;
            this.lblWTAPotWon.Text = "Pot Won:";
            // 
            // nudWTABoardDealed
            // 
            this.nudWTABoardDealed.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTABoardDealed.Location = new System.Drawing.Point(102, 38);
            this.nudWTABoardDealed.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTABoardDealed.Name = "nudWTABoardDealed";
            this.nudWTABoardDealed.Size = new System.Drawing.Size(84, 20);
            this.nudWTABoardDealed.TabIndex = 6;
            this.nudWTABoardDealed.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // lblWTABoardDealed
            // 
            this.lblWTABoardDealed.AutoSize = true;
            this.lblWTABoardDealed.Location = new System.Drawing.Point(99, 22);
            this.lblWTABoardDealed.Name = "lblWTABoardDealed";
            this.lblWTABoardDealed.Size = new System.Drawing.Size(75, 13);
            this.lblWTABoardDealed.TabIndex = 8;
            this.lblWTABoardDealed.Text = "Board Dealed:";
            // 
            // nudWTAPlayerAction
            // 
            this.nudWTAPlayerAction.Increment = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudWTAPlayerAction.Location = new System.Drawing.Point(9, 38);
            this.nudWTAPlayerAction.Maximum = new decimal(new int[] {
            60000,
            0,
            0,
            0});
            this.nudWTAPlayerAction.Name = "nudWTAPlayerAction";
            this.nudWTAPlayerAction.Size = new System.Drawing.Size(84, 20);
            this.nudWTAPlayerAction.TabIndex = 5;
            this.nudWTAPlayerAction.Value = new decimal(new int[] {
            500,
            0,
            0,
            0});
            // 
            // lblWTAPlayerAction
            // 
            this.lblWTAPlayerAction.AutoSize = true;
            this.lblWTAPlayerAction.Location = new System.Drawing.Point(6, 22);
            this.lblWTAPlayerAction.Name = "lblWTAPlayerAction";
            this.lblWTAPlayerAction.Size = new System.Drawing.Size(72, 13);
            this.lblWTAPlayerAction.TabIndex = 6;
            this.lblWTAPlayerAction.Text = "Player Action:";
            // 
            // btnAdd
            // 
            this.btnAdd.Location = new System.Drawing.Point(15, 167);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(291, 36);
            this.btnAdd.TabIndex = 10;
            this.btnAdd.Text = "Create Table";
            this.btnAdd.UseVisualStyleBackColor = true;
            this.btnAdd.Click += new System.EventHandler(this.btnAdd_Click);
            // 
            // AddTableForm
            // 
            this.AcceptButton = this.btnAdd;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(318, 215);
            this.Controls.Add(this.btnAdd);
            this.Controls.Add(this.grpWTA);
            this.Controls.Add(this.nudNbPlayers);
            this.Controls.Add(this.lblNbPlayers);
            this.Controls.Add(this.nudBigBlindAmnt);
            this.Controls.Add(this.lblBigBlindAmnt);
            this.Controls.Add(this.clstGameLimit);
            this.Controls.Add(this.lblGameLimit);
            this.Controls.Add(this.txtTableName);
            this.Controls.Add(this.lblTableName);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "AddTableForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Add Table";
            ((System.ComponentModel.ISupportInitialize)(this.nudBigBlindAmnt)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudNbPlayers)).EndInit();
            this.grpWTA.ResumeLayout(false);
            this.grpWTA.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPotWon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTABoardDealed)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudWTAPlayerAction)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblTableName;
        private System.Windows.Forms.TextBox txtTableName;
        private System.Windows.Forms.Label lblGameLimit;
        private System.Windows.Forms.ComboBox clstGameLimit;
        private System.Windows.Forms.Label lblBigBlindAmnt;
        private System.Windows.Forms.NumericUpDown nudBigBlindAmnt;
        private System.Windows.Forms.NumericUpDown nudNbPlayers;
        private System.Windows.Forms.Label lblNbPlayers;
        private System.Windows.Forms.GroupBox grpWTA;
        private System.Windows.Forms.NumericUpDown nudWTAPotWon;
        private System.Windows.Forms.Label lblWTAPotWon;
        private System.Windows.Forms.NumericUpDown nudWTABoardDealed;
        private System.Windows.Forms.Label lblWTABoardDealed;
        private System.Windows.Forms.NumericUpDown nudWTAPlayerAction;
        private System.Windows.Forms.Label lblWTAPlayerAction;
        private System.Windows.Forms.Button btnAdd;
    }
}


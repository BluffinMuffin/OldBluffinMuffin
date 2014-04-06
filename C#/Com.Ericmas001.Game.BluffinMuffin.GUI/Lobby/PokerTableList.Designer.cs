namespace Com.Ericmas001.Game.BluffinMuffin.GUI.Lobby
{
    partial class PokerTableList
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            this.datTables = new System.Windows.Forms.DataGridView();
            this.ID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.PokerTableName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.GameType = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.BigBlind = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.NbPlayers = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.datTables)).BeginInit();
            this.SuspendLayout();
            // 
            // datTables
            // 
            this.datTables.AllowUserToAddRows = false;
            this.datTables.AllowUserToDeleteRows = false;
            this.datTables.BackgroundColor = System.Drawing.SystemColors.Control;
            this.datTables.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.datTables.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.ID,
            this.PokerTableName,
            this.GameType,
            this.BigBlind,
            this.NbPlayers});
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            dataGridViewCellStyle1.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.datTables.DefaultCellStyle = dataGridViewCellStyle1;
            this.datTables.Dock = System.Windows.Forms.DockStyle.Fill;
            this.datTables.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.datTables.Location = new System.Drawing.Point(0, 0);
            this.datTables.MultiSelect = false;
            this.datTables.Name = "datTables";
            this.datTables.ReadOnly = true;
            this.datTables.RowHeadersVisible = false;
            this.datTables.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.datTables.ShowEditingIcon = false;
            this.datTables.Size = new System.Drawing.Size(564, 342);
            this.datTables.TabIndex = 9;
            this.datTables.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.datTables_CellClick);
            this.datTables.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.datTables_CellClick);
            this.datTables.CellContentDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.datTables_CellDoubleClick);
            this.datTables.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.datTables_CellDoubleClick);
            this.datTables.SelectionChanged += new System.EventHandler(this.datTables_SelectionChanged);
            // 
            // ID
            // 
            this.ID.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.ID.HeaderText = "ID";
            this.ID.Name = "ID";
            this.ID.ReadOnly = true;
            this.ID.Width = 43;
            // 
            // PokerTableName
            // 
            this.PokerTableName.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.PokerTableName.HeaderText = "Name";
            this.PokerTableName.Name = "PokerTableName";
            this.PokerTableName.ReadOnly = true;
            // 
            // GameType
            // 
            this.GameType.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.GameType.HeaderText = "Game Type";
            this.GameType.Name = "GameType";
            this.GameType.ReadOnly = true;
            this.GameType.Width = 87;
            // 
            // BigBlind
            // 
            this.BigBlind.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.BigBlind.HeaderText = "Big Blind";
            this.BigBlind.Name = "BigBlind";
            this.BigBlind.ReadOnly = true;
            this.BigBlind.Width = 73;
            // 
            // NbPlayers
            // 
            this.NbPlayers.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.NbPlayers.HeaderText = "Nb. Players";
            this.NbPlayers.Name = "NbPlayers";
            this.NbPlayers.ReadOnly = true;
            this.NbPlayers.Width = 86;
            // 
            // PokerTableList
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.datTables);
            this.Name = "PokerTableList";
            this.Size = new System.Drawing.Size(564, 342);
            ((System.ComponentModel.ISupportInitialize)(this.datTables)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView datTables;
        private System.Windows.Forms.DataGridViewTextBoxColumn ID;
        private System.Windows.Forms.DataGridViewTextBoxColumn PokerTableName;
        private System.Windows.Forms.DataGridViewTextBoxColumn GameType;
        private System.Windows.Forms.DataGridViewTextBoxColumn BigBlind;
        private System.Windows.Forms.DataGridViewTextBoxColumn NbPlayers;
    }
}
